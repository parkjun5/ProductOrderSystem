package kr.co.system.homework.legacy.order.v2.infra;

import kr.co.system.homework.legacy.order.v2.domain.OrderV2;
import kr.co.system.homework.legacy.order.v2.domain.OrderV2Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderV2Repository extends JpaRepository<OrderV2, Long>, OrderV2Repository {
}
