package kr.co.system.homework.product.application;

import kr.co.system.homework.order.application.OrderItemService;
import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.order.domain.CartItem;
import kr.co.system.homework.product.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class CartProductStockValidator {

    private final ProductService productService;
    private final OrderItemService orderItemService;

    public CartProductStockValidator(ProductService productService, OrderItemService orderItemService) {
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    public void validateStockInCart(Cart cart) {
        for (CartItem cartItem : cart.getCartItems()) {
            long productId = cartItem.getProductId();
            Product product = productService.getProductByProductId(productId);
            int alreadyOrderedQuantity = orderItemService.getAlreadyOrderedQuantityByProductId(productId);
            product.ensureOrderStock(cartItem.getQuantity(), alreadyOrderedQuantity);
        }
    }
}
