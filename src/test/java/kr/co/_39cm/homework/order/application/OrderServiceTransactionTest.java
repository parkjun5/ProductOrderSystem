package kr.co._39cm.homework.order.application;

import kr.co._39cm.homework.order.domain.OrderRepository;
import kr.co._39cm.homework.order.ui.MockOneReturnOrderInputHandler;
import kr.co._39cm.homework.order.ui.OrderInputHandlerInterface;
import kr.co._39cm.homework.product.domain.ProductRepository;
import kr.co._39cm.homework.support.exception.SoldOutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static kr.co._39cm.homework.order.domain.CartFixture.cart;
import static kr.co._39cm.homework.product.domain.ProductFixture.product;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OrderServiceTransactionTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    private final OrderInputHandlerInterface orderInputHandler = new MockOneReturnOrderInputHandler();

    @AfterEach
    public void clear() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @DisplayName("상품 주문을 multi thread 요청으로 보냈을 시에 재고가 부족하면, SoldOutException으로 리턴되어야 한다.")
    @ParameterizedTest
    @ValueSource(ints = { 5000, 10000, 20000 })
    void testConcurrent(int nThreads) {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger soldOutCount = new AtomicInteger(0);
        productRepository.save(product(orderInputHandler.listenProductId(), "1"));

        // when
        for (int i = 0; i < nThreads; i++) {
            executorService.submit(() -> {
                try {
                    orderService.createOrderOf(cart(orderInputHandler.listenProductId(), orderInputHandler.listenQuantity()));
                    successCount.incrementAndGet();
                } catch (SoldOutException e) {
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
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(soldOutCount.get()).isEqualTo(nThreads - 1);
    }

    @DisplayName("한정된 수량에 동시 주문 요청에서 재고 초과 시 SoldOutException 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"10", "50", "100"})
    void testOne(String remainStock) {
        // given
        int nThreads = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger soldOutCount = new AtomicInteger(0);
        productRepository.save(product(orderInputHandler.listenProductId(), remainStock));

        // when
        String orderJustOneQuantity = "1";
        for (int i = 0; i < nThreads; i++) {
            executorService.submit(() -> {
                try {
                    orderService.createOrderOf(cart(orderInputHandler.listenProductId(), orderJustOneQuantity));
                    successCount.incrementAndGet();
                } catch (SoldOutException e) {
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
        int remain = Integer.parseInt(remainStock);
        assertThat(successCount.get()).isEqualTo(remain);
        assertThat(soldOutCount.get()).isEqualTo(nThreads - remain);
    }

}