package kr.co._39cm.homework.order.v2.ui;

import java.util.List;

public record OrderResponse(
        Long orderId,
        String totalPrice,
        String deliveryFee,
        String totalPriceWithDeliveryFee,
        List<OrderItemResponse> orderItemResponses
) {
}
