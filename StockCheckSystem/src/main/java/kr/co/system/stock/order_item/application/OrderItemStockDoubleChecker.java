package kr.co.system.stock.order_item.application;

import kr.co.system.stock.order_item.domain.OrderItem;
import kr.co.system.stock.order_item_record.application.OrderedItemRecordService;
import kr.co.system.stock.order_item_record.domain.OrderItemRecord;
import org.springframework.stereotype.Component;
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

    @Transactional
    public void checkOrderItemHasEnoughStock(OrderItem orderItem) {
        List<OrderItemRecord> orderItemRecordsWithLock = orderedItemRecordService.getOrderItemRecordsWithLockBy(orderItem.getProductId());
        int alreadyOrderedQuantity = orderItemRecordsWithLock.stream()
                .map(OrderItemRecord::getOrderedQuantity)
                .reduce(0, Integer::sum);
        orderItemService.changeStatusByStock(orderItem, alreadyOrderedQuantity);
    }

}


