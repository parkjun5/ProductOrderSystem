package kr.co.system.stock.order_item;

import java.util.List;

public interface OrderItemRecordRepository {
    List<OrderItemRecord> findByProductIdWithPessimisticLock(long productId);
    OrderItemRecord save(OrderItemRecord orderItemRecord);
}
