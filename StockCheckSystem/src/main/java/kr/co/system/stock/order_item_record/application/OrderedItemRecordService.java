package kr.co.system.stock.order_item_record.application;

import kr.co.system.stock.order_item.domain.OrderItem;
import kr.co.system.stock.order_item_record.domain.OrderItemRecord;
import kr.co.system.stock.order_item_record.domain.OrderItemRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderedItemRecordService {

    private final OrderItemRecordRepository orderItemRecordRepository;

    public OrderedItemRecordService(OrderItemRecordRepository orderItemRecordRepository) {
        this.orderItemRecordRepository = orderItemRecordRepository;
    }

    @Transactional
    public void saveOrderItemRecord(OrderItem orderItem) {
        OrderItemRecord orderItemRecord = OrderItemRecord.of(orderItem.getId(), orderItem.getProductId(), orderItem.getSelectedQuantity());
        orderItemRecordRepository.save(orderItemRecord);
    }

    public List<OrderItemRecord> getOrderItemRecordsWithLockBy(Long productId) {
        return orderItemRecordRepository.findByProductIdWithPessimisticLock(productId);
    }

}
