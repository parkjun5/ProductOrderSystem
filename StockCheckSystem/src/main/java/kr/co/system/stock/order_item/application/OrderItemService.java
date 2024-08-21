package kr.co.system.stock.order_item.application;

import kr.co.system.stock.order_item.domain.OrderItem;
import kr.co.system.stock.order_item.infa.JpaOrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemService {

    private final JpaOrderItemRepository jpaOrderItemRepository;

    public OrderItemService(JpaOrderItemRepository jpaOrderItemRepository) {
        this.jpaOrderItemRepository = jpaOrderItemRepository;
    }

    @Transactional
    public void checkHasEnoughStock(OrderItem orderItem, int alreadyOrderedQuantity) {
        orderItem.updateOrderItemStatusBasedOnStock(alreadyOrderedQuantity);
        jpaOrderItemRepository.save(orderItem);
    }

}
