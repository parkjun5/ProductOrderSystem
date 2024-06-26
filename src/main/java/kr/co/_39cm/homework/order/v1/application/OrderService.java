package kr.co._39cm.homework.order.v1.application;

import kr.co._39cm.homework.order.common.domain.Cart;
import kr.co._39cm.homework.order.common.domain.CartItem;
import kr.co._39cm.homework.order.v1.domain.Order;
import kr.co._39cm.homework.order.v1.domain.OrderItem;
import kr.co._39cm.homework.order.v1.domain.OrderRepository;
import kr.co._39cm.homework.product.v1.application.ProductService;
import kr.co._39cm.homework.product.v1.domain.Product;
import kr.co._39cm.homework.support.exception.SoldOutException;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;

    public OrderService(ProductService productService, OrderRepository orderRepository) {
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    /**
     * 주어진 장바구니({@code Cart})에 대한 주문을 생성한다.
     * 장바구니 항목에 대해 제품의 재고를 확인하고, 재고가 충분한 경우에만 주문 항목을 생성한다.
     *
     * 주문 생성 과정에서 발생할 수 있는 동시성 문제를 해결하기 위해 비관적 락(Pessimistic Locking)을 사용한다.
     * 비관적 락을 통해 동시에 많은 주문 요청에서 발생하는 동시성 이슈를 해결하고 데이터의 일관성을 유지한다.
     *
     * {@link Transactional} 어노테이션에서 트랜잭션의 격리 수준을 REPEATABLE READ로 설정하여,
     * 트랜잭션 내에서 동일한 쿼리 결과의 일관성을 보장한다. 이는 더티 리드를 방지하여 데이터 일관성을 유지한다.
     *
     * <p>예외 처리:
     * <ul>
     *   <li>만약 제품의 재고가 충분하지 않아 주문을 생성할 수 없는 경우, {@link SoldOutException}이 발생한다.</li>
     *   <li>장바구니가 비어있거나 유효하지 않은 경우, {@link IllegalArgumentException}이 발생할 수 있다.</li>
     * </ul>
     *
     * @param cart 주문을 생성할 장바구니
     * @return 생성된 주문 객체
     * @throws SoldOutException 재고가 부족하여 주문을 생성할 수 없는 경우
     * @throws IllegalArgumentException 장바구니가 비어있거나 유효하지 않은 경우
     */
    @Retryable(
            retryFor = LockAcquisitionException.class,
            backoff = @Backoff(delay = 500)
    )
    @Transactional(timeout = 2000, isolation = Isolation.REPEATABLE_READ)
    public Order createOrderOf(Cart cart) {
        productService.validateStockInCart(cart);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = productService.getProductWithLockBy(cartItem.getProductId());
            orderItems.add(new OrderItem(product, cartItem.getQuantity(), product.getProductInfo()));
            productService.deduct(product, cartItem.getQuantity());
        }

        Order order = new Order(orderItems);

        orderRepository.save(order);
        return order;
    }

}
