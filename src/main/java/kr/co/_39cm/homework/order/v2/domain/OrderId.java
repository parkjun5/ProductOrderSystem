package kr.co._39cm.homework.order.v2.domain;

import kr.co._39cm.homework.support.domain.LongTypeIdentifier;
import kr.co._39cm.homework.support.jpa.hibernate.LongTypeIdentifierJavaType;
import org.hibernate.type.descriptor.WrapperOptions;

public class OrderId extends LongTypeIdentifier {

    public static OrderId of(Long id) {
        return new OrderId(id);
    }
    public OrderId(Long id) {
        super(id);
    }

    public static class OrderIdJavaType extends LongTypeIdentifierJavaType<OrderId> {
        public OrderIdJavaType() {
            super(OrderId.class);
        }

        @Override
        public <X> OrderId wrap(X value, WrapperOptions options) {
            if (value == null) return null;
            if (value instanceof OrderId orderId) {
                return orderId;
            }
            if (value instanceof Long longValue) {
                return OrderId.of(longValue);
            }
            throw unknownWrap(value.getClass());
        }
    }
}
