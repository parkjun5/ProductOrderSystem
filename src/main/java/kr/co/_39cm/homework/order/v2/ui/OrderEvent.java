package kr.co._39cm.homework.order.v2.ui;

import java.util.List;

public record OrderEvent(
        Long id,
        List<OrderItemEvent> orderItemResults
) {
}
