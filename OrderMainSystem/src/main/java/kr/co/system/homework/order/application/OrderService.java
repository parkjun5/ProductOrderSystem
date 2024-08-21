package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.order.domain.Order;
import kr.co.system.homework.order.domain.OrderItem;
import kr.co.system.homework.order.domain.OrderRepository;
import kr.co.system.homework.order.ui.dto.OrderResponse;
import kr.co.system.homework.product.application.CarItemConverter;
import kr.co.system.homework.product.application.CartProductStockValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final CarItemConverter carItemConverter;
    private final CartProductStockValidator cartProductStockValidator;
    private final OrderRepository orderRepository;

    public OrderService(CarItemConverter carItemConverter,
                        CartProductStockValidator cartProductStockValidator,
                        OrderRepository orderRepository
    ) {
        this.carItemConverter = carItemConverter;
        this.cartProductStockValidator = cartProductStockValidator;
        this.orderRepository = orderRepository;
    }

    @Transactional(timeout = 1000)
    public OrderResponse createOrderBy(Cart cart) {
        cartProductStockValidator.validateStockInCart(cart);
        List<OrderItem> orderItems = carItemConverter.convertToOrderItems(cart);

        Order order = Order.of(orderItems);
        order.registerEvent();
        orderRepository.save(order);

        return OrderResponse.from(order);
    }

}
