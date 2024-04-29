package kr.co._29cm.homework.order.ui;

import kr.co._29cm.homework.order.application.OrderService;
import kr.co._29cm.homework.order.domain.Cart;
import kr.co._29cm.homework.order.domain.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{productId}/quantity/{quantity}")
    public ResponseEntity<String> singleOrder(@PathVariable(name = "productId") String productId,
                                              @PathVariable(name = "quantity") String quantity
    ) {
        Cart cart = Cart.of(productId, quantity);
        Order order = orderService.createOrderOf(cart);
        String body = """
                OrderId => %s
                Order Delivery Fee => %s
                """.formatted(order.getId(), order.getFormattedTotalPriceWithDeliveryFee());
        return ResponseEntity.ok(body);
    }
}
