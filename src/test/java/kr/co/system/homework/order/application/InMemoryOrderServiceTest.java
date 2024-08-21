package kr.co.system.homework.order.application;

import kr.co.system.homework.legacy.order.v1.application.OrderV1Service;
import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.legacy.order.v1.domain.OrderV1;
import kr.co.system.homework.order.infra.InMemoryOrderV1Repository;
import kr.co.system.homework.legacy.product.v1.application.ProductV1Converter;
import kr.co.system.homework.legacy.product.v1.application.ProductV1Service;
import kr.co.system.homework.legacy.product.v1.domain.ProductV1;
import kr.co.system.homework.product.infra.InMemoryProductV1Repository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static kr.co.system.homework.order.domain.CartFixture.cart;
import static kr.co.system.homework.product.domain.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryOrderServiceTest {

    private final InMemoryProductV1Repository productRepository = new InMemoryProductV1Repository();
    private final ProductV1Service productService = new ProductV1Service(new ProductV1Converter(), productRepository);
    private final InMemoryOrderV1Repository orderRepository = new InMemoryOrderV1Repository();
    private final OrderV1Service orderService = new OrderV1Service(productService, orderRepository);
    @ParameterizedTest
    @ValueSource(strings = {"1", "3", "5", "7"})
    @DisplayName("주문된 만큼 재고를 변경한다.")
    void updateStockAfterOrder(String requiredQuantity) {
        // given
        ProductV1 product = product("10");
        productRepository.save(product);
        Cart cart = cart(product.getId().toString(), requiredQuantity);

        // when
        OrderV1 order = orderService.createOrderOf(cart);

        // then
        int except = 10 - Integer.parseInt(requiredQuantity);
        assertThat(product.getStock()).isEqualTo(except);
        Assertions.assertThat(order.getOrderItems().get(0).getProductV1().getStock()).isEqualTo(except);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "3", "5", "7"})
    @DisplayName("모든 주문에 포함된 상품의 재고가 변경되어야한다.")
    void updateStockAfterOrders(String requiredQuantity) {
        // given
        ProductV1 product1 = product("1", "10");
        ProductV1 product2 = product("2", "15");
        ProductV1 product3 = product("3", "20");
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
