package kr.co._39cm.homework.order.application;

import kr.co._39cm.homework.order.ui.OrderItemResponse;
import kr.co._39cm.homework.legacy.product.v2.application.ProductV2Service;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemStockDoubleChecker {

    private final ProductV2Service productV2Service;

    public OrderItemStockDoubleChecker(ProductV2Service productV2Service) {
        this.productV2Service = productV2Service;
    }

    public boolean doubleCheckOrderItemStock(List<OrderItemResponse> orderItemResponses) {
        orderItemResponses.forEach(it -> {
        });
        return false;
    }
}


