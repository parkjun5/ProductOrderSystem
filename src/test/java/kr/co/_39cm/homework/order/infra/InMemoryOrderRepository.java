package kr.co._39cm.homework.order.infra;

import kr.co._39cm.homework.order.domain.Order;
import kr.co._39cm.homework.order.domain.OrderRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<Long, Order> orders = new HashMap<>();

    @Override
    public Order save(Order order) {
        return orders.put(order.getId(), order);
    }

    @Override
    public void deleteAll() {
        orders.clear();
    }
}