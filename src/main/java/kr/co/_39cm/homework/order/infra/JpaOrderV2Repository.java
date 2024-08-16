package kr.co._39cm.homework.order.infra;

import kr.co._39cm.homework.order.domain.OrderV2;
import kr.co._39cm.homework.order.domain.OrderV2Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderV2Repository extends JpaRepository<OrderV2, Long>, OrderV2Repository {
}
