package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.OrderItem;
import kr.co.system.homework.order.domain.OrderItemRecord;
import kr.co.system.homework.order.domain.OrderItemRecordRepository;
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
