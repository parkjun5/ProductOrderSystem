package kr.co._39cm.homework.order.v2.infra;

import kr.co._39cm.homework.order.v2.domain.OrderId;
import kr.co._39cm.homework.order.v2.domain.OrderV2;
import kr.co._39cm.homework.order.v2.domain.OrderV2Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderV2Repository extends JpaRepository<OrderV2, OrderId>, OrderV2Repository {
}
