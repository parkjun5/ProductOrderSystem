package kr.co.system.homework.order.domain;

import kr.co.system.homework.legacy.order.v1.domain.OrderV1;
import kr.co.system.homework.legacy.order.v1.domain.OrderItemV1;

import java.util.List;

public class OrderFixture {

    public static OrderV1 order(OrderItemV1 e1) {
        return order(List.of(e1));
    }

    public static OrderV1 order(List<OrderItemV1> e11) {
        return new OrderV1(e11);
    }
}
