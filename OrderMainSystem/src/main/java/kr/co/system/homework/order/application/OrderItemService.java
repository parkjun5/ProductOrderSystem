package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.OrderItem;
import kr.co.system.homework.order.domain.OrderItemStatus;
import kr.co.system.homework.order.infra.JpaOrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemService {

    private final JpaOrderItemRepository jpaOrderItemRepository;

    public OrderItemService(JpaOrderItemRepository jpaOrderItemRepository) {
        this.jpaOrderItemRepository = jpaOrderItemRepository;
    }

    @Transactional
    public int getAlreadyOrderedQuantityByProductId(long productId) {
        Integer orderedQuantity = jpaOrderItemRepository.sumSelectedQuantityByProductIdAndExcludeStatusesWithOptimisticLock(
                productId,
                OrderItemStatus.EXCLUDE_STATUSES
        );
        return orderedQuantity != null ? orderedQuantity : 0;
    }
}
