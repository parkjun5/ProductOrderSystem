package kr.co.system.homework.order.ui.dto;

import kr.co.system.homework.order.domain.Order;

import java.util.List;

public record OrderResponse(
        Long orderId,
        String formattedTotalPrice,
        boolean hasDeliveryFee,
        String formattedDeliveryFee,
        String formattedTotalPriceWithDeliveryFee,
        List<OrderItemResponse> orderItemResponses
) {

    public static OrderResponse from(Order order) {
        var orderItemResponses = order.getOrderItems().stream()
                .map(OrderItemResponse::from)
                .toList();
        return new OrderResponse(
                order.getId(),
                order.getFormattedTotalPrice(),
                order.hasDeliveryFee(),
                order.getFormattedDeliveryFee(),
                order.getFormattedTotalPriceWithDeliveryFee(),
                orderItemResponses
        );
    }
}
