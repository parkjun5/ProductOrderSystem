package kr.co.system.homework.order.ui;

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
