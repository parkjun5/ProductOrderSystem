package kr.co._29cm.homework.order.application;

import kr.co._29cm.homework.order.domain.Cart;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.infra.InMemoryOrderRepository;
import kr.co._29cm.homework.product.application.ProductConverter;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.infra.InMemoryProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static kr.co._29cm.homework.order.domain.CartFixture.cart;
import static kr.co._29cm.homework.product.domain.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryOrderServiceTest {

    private final InMemoryProductRepository productRepository = new InMemoryProductRepository();
    private final ProductService productService = new ProductService(new ProductConverter(), productRepository);
    private final InMemoryOrderRepository orderRepository = new InMemoryOrderRepository();
    private final OrderService orderService = new OrderService(productService, orderRepository);
    @ParameterizedTest
    @ValueSource(strings = {"1", "3", "5", "7"})
    @DisplayName("주문된 만큼 재고를 변경한다.")
    void updateStockAfterOrder(String requiredQuantity) {
        // given
        Product product = product("10");
        productRepository.save(product);
        Cart cart = cart(product.getId().toString(), requiredQuantity);

        // when
        Order order = orderService.createOrderOf(cart);

        // then
        int except = 10 - Integer.parseInt(requiredQuantity);
        assertThat(product.getStock()).isEqualTo(except);
        assertThat(order.getOrderItems().get(0).getProduct().getStock()).isEqualTo(except);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "3", "5", "7"})
    @DisplayName("모든 주문에 포함된 상품의 재고가 변경되어야한다.")
    void updateStockAfterOrders(String requiredQuantity) {
        // given
        Product product1 = product("1", "10");
        Product product2 = product("2", "15");
        Product product3 = product("3", "20");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        Cart cart = cart();
        cart.putItem(product1.getId().toString(), requiredQuantity);
        cart.putItem(product2.getId().toString(), requiredQuantity);
        cart.putItem(product3.getId().toString(), requiredQuantity);

        // when
        orderService.createOrderOf(cart);

        // then
        int quantity = Integer.parseInt(requiredQuantity);
        assertThat(product1.getStock()).isEqualTo(10 - quantity);
        assertThat(product2.getStock()).isEqualTo(15 - quantity);
        assertThat(product3.getStock()).isEqualTo(20 - quantity);
    }


}
