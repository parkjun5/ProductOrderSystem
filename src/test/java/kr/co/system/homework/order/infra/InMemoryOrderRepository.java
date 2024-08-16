package kr.co.system.homework.order.infra;

import kr.co.system.homework.legacy.order.v1.domain.OrderV1;
import kr.co.system.homework.legacy.order.v1.domain.OrderV1Repository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryOrderRepository implements OrderV1Repository {
    private final Map<Long, OrderV1> orders = new HashMap<>();

    @Override
    public OrderV1 save(OrderV1 order) {
        return orders.put(order.getId(), order);
    }

    @Override
    public void deleteAll() {
        orders.clear();
    }
}
