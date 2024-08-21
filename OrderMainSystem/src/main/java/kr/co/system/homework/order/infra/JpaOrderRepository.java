package kr.co.system.homework.order.infra;

import kr.co.system.homework.order.domain.Order;
import kr.co.system.homework.order.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long>, OrderRepository {
}
