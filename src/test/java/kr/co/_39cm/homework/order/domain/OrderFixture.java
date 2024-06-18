package kr.co._39cm.homework.order.domain;

import kr.co._39cm.homework.order.v1.domain.Order;
import kr.co._39cm.homework.order.v1.domain.OrderItem;

import java.util.List;

public class OrderFixture {

    public static Order order(OrderItem e1) {
        return order(List.of(e1));
    }

    public static Order order(List<OrderItem> e11) {
        return new Order(e11);
    }
}
