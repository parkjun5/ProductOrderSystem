package kr.co.system.homework.order.application;

import kr.co.system.homework.order.ui.dto.OrderItemResponse;
import kr.co.system.homework.product.application.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemStockDoubleChecker {

    private final ProductService productService;

    public OrderItemStockDoubleChecker(ProductService productService) {
        this.productService = productService;
    }

    public boolean doubleCheckOrderItemStock(List<OrderItemResponse> orderItemResponses) {
        orderItemResponses.forEach(it -> {
        });
        return false;
    }
}


