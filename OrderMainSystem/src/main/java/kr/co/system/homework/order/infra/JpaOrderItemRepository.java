package kr.co.system.homework.order.infra;

import jakarta.persistence.LockModeType;
import kr.co.system.homework.order.domain.OrderItem;
import kr.co.system.homework.order.domain.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaOrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
            SELECT
                SUM(oi.selectedQuantity)
            FROM
                OrderItem oi
            WHERE
                oi.product.id = :productId
                AND oi.orderItemStatus NOT IN (:excludedStatuses)
            """)
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Integer sumSelectedQuantityByProductIdAndExcludeStatusesWithOptimisticLock(@Param("productId") long productId,
                                                                               @Param("excludedStatuses") List<OrderItemStatus> excludedStatuses);
}
