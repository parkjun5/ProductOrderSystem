package kr.co._29cm.homework.order.infra;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long>, OrderRepository {
}
