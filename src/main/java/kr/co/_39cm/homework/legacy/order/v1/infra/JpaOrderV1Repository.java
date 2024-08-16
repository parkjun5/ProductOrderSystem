package kr.co._39cm.homework.legacy.order.v1.infra;

import kr.co._39cm.homework.legacy.order.v1.domain.OrderV1;
import kr.co._39cm.homework.legacy.order.v1.domain.OrderV1Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderV1Repository extends JpaRepository<OrderV1, Long>, OrderV1Repository {
}
