package kr.co.system.stock.order_item;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaOrderItemRecordRepository extends JpaRepository<OrderItemRecord, Long>, OrderItemRecordRepository {
    @Lock(value = LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Query("SELECT oir FROM OrderItemRecord oir WHERE oir.productId = :productId")
    List<OrderItemRecord> findByProductIdWithPessimisticLock(@Param("productId") long productId);
}
