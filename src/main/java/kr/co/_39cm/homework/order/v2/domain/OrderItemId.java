package kr.co._39cm.homework.order.v2.domain;

import kr.co._39cm.homework.support.domain.LongTypeIdentifier;
import kr.co._39cm.homework.support.jpa.hibernate.LongTypeIdentifierJavaType;

public class OrderItemId extends LongTypeIdentifier {
    public static OrderItemId of(Long id) {
        return new OrderItemId(id);
    }

    public OrderItemId(Long id) {
        super(id);
    }

    public static class OrderItemIdJavaType extends LongTypeIdentifierJavaType<OrderItemId> {
        public OrderItemIdJavaType() {
            super(OrderItemId.class);
        }
    }
}
