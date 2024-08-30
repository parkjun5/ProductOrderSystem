package kr.co.system.stock.order_item;

import kr.co.system.stock.order_item.application.OrderItemStockDoubleChecker;
import kr.co.system.stock.order_item.domain.OrderItem;
import kr.co.system.stock.order_item_record.application.OrderedItemRecordService;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderItemDoubleChecker {

    private final OrderedItemRecordService orderedItemRecordService;
    private final OrderItemStockDoubleChecker orderItemStockDoubleChecker;

    public OrderItemDoubleChecker(OrderedItemRecordService orderedItemRecordService,
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
    public void startCheck(OrderItem orderItem) {
        orderedItemRecordService.saveOrderItemRecord(orderItem);
        orderItemStockDoubleChecker.checkOrderItemHasEnoughStock(orderItem);
    }
}
