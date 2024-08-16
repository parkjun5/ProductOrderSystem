package kr.co._39cm.homework.order.ui;

public record OrderItemEvent(
        Long productId,
        int quantity
) {
}
