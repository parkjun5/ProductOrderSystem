package kr.co.system.homework.order.domain;

import java.util.List;

public enum OrderItemStatus {
    ORDERED,
    ACCEPTED,
    CANCELED,
    DELIVERING,
    DELIVERED,
    COMPLETED,
    RETURNED;

    public static final List<OrderItemStatus> EXCLUDE_STATUSES = List.of(CANCELED, ORDERED);

}
