package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventHandler {

    private final OrderItemStockDoubleChecker orderItemStockDoubleChecker;

    public OrderEventHandler(OrderItemStockDoubleChecker orderItemStockDoubleChecker) {
        this.orderItemStockDoubleChecker = orderItemStockDoubleChecker;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderEvent(Order order) {
        boolean isAvailableOrderItem = orderItemStockDoubleChecker.doubleCheckOrderItemStock(order);
        if (isAvailableOrderItem) {

        }
    }

}
