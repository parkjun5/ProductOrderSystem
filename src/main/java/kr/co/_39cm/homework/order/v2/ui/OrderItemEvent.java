package kr.co._39cm.homework.order.v2.ui;

public record OrderItemEvent(
        Long productId,
        int quantity
) {
}
