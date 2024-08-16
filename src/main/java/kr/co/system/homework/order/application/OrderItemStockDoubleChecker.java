package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.Order;
import kr.co.system.homework.order.domain.OrderItem;
import kr.co.system.homework.product.application.ProductService;
import kr.co.system.homework.product.domain.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderItemStockDoubleChecker {

    private final ProductService productService;

    public OrderItemStockDoubleChecker(ProductService productService) {
        this.productService = productService;
    }

    public boolean hasNotEnoughStockInOrder(OrderItem orderItem, LocalDateTime orderTime) {
        final Product product = orderItem.getProduct();
        int previousOrderedQuantity = orderItemService.getPreviousOrderedQuantityLessThanOrderTime(product.getId(), orderTime);
        return product.hasNotEnoughStock(previousOrderedQuantity, orderItem.getSelectedQuantity());
    }

}


