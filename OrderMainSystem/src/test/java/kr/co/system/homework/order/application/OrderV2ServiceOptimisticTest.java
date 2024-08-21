package kr.co.system.homework.order.application;

import kr.co.system.homework.legacy.order.v2.application.OrderV2Service;
import kr.co.system.homework.legacy.order.v2.domain.OrderItemV2Repository;
import kr.co.system.homework.legacy.product.v2.domain.ProductV2;
import kr.co.system.homework.legacy.product.v2.domain.ProductV2Repository;
import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.order.domain.OrderRepository;
import kr.co.system.homework.order.ui.MockOneReturnOrderInputHandler;
import kr.co.system.homework.order.ui.OrderInputHandlerInterface;
import kr.co.system.homework.order.ui.dto.OrderResponse;
import kr.co.system.homework.product.domain.ProductV2Fixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static kr.co.system.homework.order.domain.CartFixture.cart;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderV2ServiceOptimisticTest {

    @Autowired
    private OrderV2Service orderV2Service;
    @Autowired
    private ProductV2Repository productV2Repository;
    @Autowired
    private OrderItemV2Repository orderItemV2Repository;
    @Autowired
    private OrderRepository orderV2Repository;
    private final OrderInputHandlerInterface orderInputHandler = new MockOneReturnOrderInputHandler();

    @AfterEach
    public void clear() {
        orderV2Repository.deleteAll();
        orderItemV2Repository.deleteAll();
        productV2Repository.deleteAll();
    }

    @DisplayName("상품 주문을 multi thread 요청으로 보냈을 시에 재고가 부족하면, SoldOutException으로 리턴되어야 한다.")
    @Test
    void singleTest() {
        // given
        productV2Repository.save(ProductV2Fixture.productV2(orderInputHandler.listenProductId(), orderInputHandler.listenQuantity()));
        OrderResponse orderOf = orderV2Service.createOrderOf(cart(orderInputHandler.listenProductId(), orderInputHandler.listenQuantity()));

        // then
        assertThat(orderOf).isNotNull();
    }

    @DisplayName("상품 주문을 multi thread 요청으로 보냈을 시에 재고가 부족하면, SoldOutException으로 리턴되어야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {10000, 20000})
    void testConcurrent(int nThreads) {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger soldOutCount = new AtomicInteger(0);
        ProductV2 save = productV2Repository.save(ProductV2Fixture.productV2(orderInputHandler.listenProductId(), "1"));
        // when
        for (int i = 0; i < nThreads; i++) {
            executorService.submit(() -> {
                try {
                    Cart cart = cart(String.valueOf(save.getId().longValue()), orderInputHandler.listenQuantity());
                    orderV2Service.createOrderOf(cart);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    soldOutCount.incrementAndGet();
                }
            });
        }

        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // then
        assertThat(successCount.get()).isNotZero();
    }
}
