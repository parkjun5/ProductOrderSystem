package kr.co.system.homework.product.application;

import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.order.domain.OrderItem;
import kr.co.system.homework.product.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarItemConverter {

    private final ProductService productService;

    public CarItemConverter(ProductService productService) {
        this.productService = productService;
    }

    public List<OrderItem> convertToOrderItems(Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem -> {
                    Product product = productService.getProductByProductId(cartItem.getProductId());
                    return OrderItem.of(product, cartItem.getQuantity());
                }).toList();
    }
}
