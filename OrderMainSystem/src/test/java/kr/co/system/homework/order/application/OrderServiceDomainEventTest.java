package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.*;
import kr.co.system.homework.order.infra.JpaOrderItemRepository;
import kr.co.system.homework.order.ui.MockOneReturnOrderInputHandler;
import kr.co.system.homework.order.ui.OrderInputHandlerInterface;
import kr.co.system.homework.order.ui.dto.OrderResponse;
import kr.co.system.homework.product.domain.Product;
import kr.co.system.homework.product.domain.ProductRepository;
import kr.co.system.homework.product.domain.ProductV3Fixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static kr.co.system.homework.order.domain.CartFixture.cart;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceDomainEventTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JpaOrderItemRepository jpaOrderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    private final OrderInputHandlerInterface orderInputHandler = new MockOneReturnOrderInputHandler();

    @AfterEach
    public void clear() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @DisplayName("상품 주문을 multi thread 요청으로 보냈을 시에 재고가 부족하면, SoldOutException으로 리턴되어야 한다.")
    @Test
    @Transactional
    void singleTest() throws InterruptedException {
        // given
        productRepository.save(ProductV3Fixture.product(orderInputHandler.listenProductId(), orderInputHandler.listenQuantity()));
        OrderResponse orderOf = orderService.createOrderBy(cart(orderInputHandler.listenProductId(), orderInputHandler.listenQuantity()));

        // then
        assertThat(orderOf).isNotNull();
    }

    @DisplayName("상품 주문을 multi thread 요청으로 보냈을 시에 재고가 부족하면, SoldOutException으로 리턴되어야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {10000, 20000})
    void testConcurrent(int nThreads) throws InterruptedException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        Product save = productRepository.save(ProductV3Fixture.product(orderInputHandler.listenProductId(), "1"));
        // when
        for (int i = 0; i < nThreads; i++) {
            executorService.submit(() -> {
                try {
                    Cart cart = cart(String.valueOf(save.getId().longValue()), orderInputHandler.listenQuantity());
                    orderService.createOrderBy(cart);
                } catch (Exception e) {
                    //DoNothing
                }
            });
        }

        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // then
        Thread.sleep(10000L);
        List<OrderItem> all = jpaOrderItemRepository.findAll();
        long allCount = all.stream().count();
        long accepted = all.stream().filter(it -> it.getOrderItemStatus() == OrderItemStatus.ACCEPTED)
                .count();
        long canceled =  all.stream().filter(it -> it.getOrderItemStatus() == OrderItemStatus.CANCELED)
                .count();

        System.out.println("allCount = " + allCount);
        System.out.println("accepted = " + accepted);
        System.out.println("canceled = " + canceled);
    }
}
