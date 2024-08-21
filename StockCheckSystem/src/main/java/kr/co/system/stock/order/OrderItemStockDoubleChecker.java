package kr.co.system.stock.order;

import kr.co.system.stock.order_item.OrderItem;
import kr.co.system.stock.order_item.OrderItemRecord;
import kr.co.system.stock.order_item.OrderItemService;
import kr.co.system.stock.order_item.OrderedItemRecordService;
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
        orderItemService.checkHasEnoughStock(orderItem, alreadyOrderedQuantity);
    }

}


