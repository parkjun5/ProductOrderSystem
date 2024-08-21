package kr.co.system.homework.order.application;

import jakarta.persistence.OptimisticLockException;
import kr.co.system.homework.order.domain.OrderItem;
import kr.co.system.homework.order.domain.OrderItemRecord;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderItemStockDoubleChecker {

    private final OrderItemService orderItemService;
    private final OrderedItemRecordService orderedItemRecordService;

    public OrderItemStockDoubleChecker(OrderItemService orderItemService,
                                       OrderedItemRecordService orderedItemRecordService
    ) {
        this.orderItemService = orderItemService;
        this.orderedItemRecordService = orderedItemRecordService;
    }

    @Retryable(
            retryFor = LockAcquisitionException.class,
            backoff = @Backoff(delay = 500)
    )
    @Transactional(timeout = 2000, isolation = Isolation.REPEATABLE_READ)
    public void checkOrderItemHasEnoughStock(OrderItem orderItem) {
        List<OrderItemRecord> orderItemRecordsWithLock = orderedItemRecordService.getOrderItemRecordsWithLockBy(orderItem.getProductId());
        int alreadyOrderedQuantity = orderItemRecordsWithLock.stream()
                .map(OrderItemRecord::getOrderedQuantity)
                .reduce(0, Integer::sum);
        orderItemService.checkHasEnoughStock(orderItem, alreadyOrderedQuantity);
    }

}


