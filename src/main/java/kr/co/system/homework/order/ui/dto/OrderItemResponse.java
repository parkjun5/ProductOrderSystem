package kr.co.system.homework.order.ui.dto;

import kr.co.system.homework.order.domain.OrderItem;
import kr.co.system.homework.product.domain.Product;

public record OrderItemResponse(
        Long productId,
        String productName,
        int quantity
) {
    public static OrderItemResponse from(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        return new OrderItemResponse(
                product.getId(),
                product.getProductInfo().getProductName(),
                orderItem.getSelectedQuantity()
        );
    }
}
