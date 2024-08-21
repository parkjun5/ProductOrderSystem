package kr.co.system.stock;

import kr.co.system.stock.order.OrderItemStockDoubleChecker;
import kr.co.system.stock.order_item.OrderItem;
import kr.co.system.stock.order_item.OrderedItemRecordService;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderDoubleChecker {

    private final OrderedItemRecordService orderedItemRecordService;
    private final OrderItemStockDoubleChecker orderItemStockDoubleChecker;

    public OrderDoubleChecker(OrderedItemRecordService orderedItemRecordService,
                              OrderItemStockDoubleChecker orderItemStockDoubleChecker
    ) {
        this.orderedItemRecordService = orderedItemRecordService;
        this.orderItemStockDoubleChecker = orderItemStockDoubleChecker;
    }

    @Retryable(
            retryFor = LockAcquisitionException.class,
            backoff = @Backoff(delay = 500)
    )
    @Transactional(timeout = 2000, isolation = Isolation.REPEATABLE_READ)
    public void startCheck(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            orderedItemRecordService.saveOrderItemRecord(orderItem);
            orderItemStockDoubleChecker.checkOrderItemHasEnoughStock(orderItem);
        }
    }
}
