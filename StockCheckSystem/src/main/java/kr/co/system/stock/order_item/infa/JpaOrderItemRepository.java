package kr.co.system.stock.order_item.infa;

import kr.co.system.stock.order_item.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderItemRepository extends JpaRepository<OrderItem, Long> {
}
