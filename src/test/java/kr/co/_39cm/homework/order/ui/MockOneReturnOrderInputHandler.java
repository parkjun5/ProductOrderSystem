package kr.co._39cm.homework.order.ui;

import kr.co._39cm.homework.order.common.ui.OrderInputHandlerInterface;

public class MockOneReturnOrderInputHandler implements OrderInputHandlerInterface {

    @Override
    public String listenProductId() {
        return "1";
    }

    @Override
    public String listenQuantity() {
        return "1";
    }
}
