package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.Order;
import kr.co.system.homework.order.domain.OrderItem;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
public class OrderEventHandler {

    private final OrderItemStockDoubleChecker orderItemStockDoubleChecker;

    public OrderEventHandler(OrderItemStockDoubleChecker orderItemStockDoubleChecker) {
        this.orderItemStockDoubleChecker = orderItemStockDoubleChecker;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderEvent(Order order) {
        LocalDateTime orderTime = order.getOrderTime();
        for (OrderItem orderItem : order.getOrderItems()) {
            boolean hasNotEnoughStock = orderItemStockDoubleChecker.hasNotEnoughStockInOrder(orderItem, orderTime);
            if (hasNotEnoughStock) {
                orderItem.rejected();
            }

        }

    }

}
