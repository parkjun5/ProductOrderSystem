package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventHandler {

    private final OrderedItemRecordService orderedItemRecordService;
    private final OrderItemStockDoubleChecker orderItemStockDoubleChecker;

    public OrderEventHandler(OrderedItemRecordService orderedItemRecordService,
                             OrderItemStockDoubleChecker orderItemStockDoubleChecker
    ) {
        this.orderedItemRecordService = orderedItemRecordService;
        this.orderItemStockDoubleChecker = orderItemStockDoubleChecker;
    }

    @Async
    @TransactionalEventListener
    public void handleOrderEvent(Order order) {
        order.getOrderItems().forEach(orderItem -> {
            orderedItemRecordService.saveOrderItemRecord(orderItem);
            orderItemStockDoubleChecker.checkOrderItemHasEnoughStock(orderItem);
        });
    }

}
