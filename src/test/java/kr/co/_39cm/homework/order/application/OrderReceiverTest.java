package kr.co._39cm.homework.order.application;

import kr.co._39cm.homework.order.ui.OrderInputHandlerInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderReceiverTest {

    private OrderReceiver orderReceiver;

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품 번호와 주문 수량이 비어 있으면 안된다.")
    void collectTest(String input) {
        // given
        OrderInputHandlerInterface orderInputHandlerInterface = new OrderInputHandlerInterface() {
            @Override
            public String listenProductId() {
                return input;
            }

            @Override
            public String listenQuantity() {
                return input;
            }
        };
        orderReceiver = new OrderReceiver(orderInputHandlerInterface);

        // when && then
        assertThatThrownBy(() -> orderReceiver.collectRequestToCart())
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 번호와 주문 수량은 비어있을 수 없습니다. 상품번호 :" + input + "수량 : " + input);
    }

    @DisplayName("상품 번호에 문자열을 입력하면 안된다.")
    @Test
    void collectTest2() {
        // given
        OrderInputHandlerInterface orderInputHandlerInterface = new OrderInputHandlerInterface() {
            @Override
            public String listenProductId() {
                return "asdasd";
            }

            @Override
            public String listenQuantity() {
                return "1";
            }
        };
        orderReceiver = new OrderReceiver(orderInputHandlerInterface);

        // when && then
        assertThatThrownBy(() -> orderReceiver.collectRequestToCart())
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 값이 입력되었습니다. orderedProductId = asdasd, orderedQuantity = 1");
    }

    @DisplayName("주문 수량에 문자열을 입력하면 안된다.")
    @Test
    void collectTest3() {
        // given
        OrderInputHandlerInterface orderInputHandlerInterface = new OrderInputHandlerInterface() {
            @Override
            public String listenProductId() {
                return "123";
            }

            @Override
            public String listenQuantity() {
                return "asdasd";
            }
        };
        orderReceiver = new OrderReceiver(orderInputHandlerInterface);

        // when && then
        assertThatThrownBy(() -> orderReceiver.collectRequestToCart())
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 값이 입력되었습니다. orderedProductId = 123, orderedQuantity = asdasd");
    }

}