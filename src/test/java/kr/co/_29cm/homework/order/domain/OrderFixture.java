package kr.co._29cm.homework.order.domain;

import java.util.List;

public class OrderFixture {

    public static Order order(OrderItem e1) {
        return order(List.of(e1));
    }

    public static Order order(List<OrderItem> e11) {
        return new Order(e11);
    }
}
