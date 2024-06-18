package kr.co._39cm.homework.order.v1.infra;

import kr.co._39cm.homework.order.v1.domain.Order;
import kr.co._39cm.homework.order.v1.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long>, OrderRepository {
}
