package kr.co.system.homework.order.domain;

import java.util.List;

public interface OrderItemRecordRepository {
    List<OrderItemRecord> findByProductIdWithPessimisticLock(long productId);
    OrderItemRecord save(OrderItemRecord orderItemRecord);
}
