package kr.co.system.homework.order.ui;

import kr.co.system.homework.order.application.OrderService;
import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.order.ui.dto.OrderResponse;
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
        OrderResponse orderResponse = orderService.createOrderOf(cart);
        String body = """
                OrderId => %s
                Order Delivery Fee => %s
                """.formatted(orderResponse.orderId(), orderResponse.formattedTotalPriceWithDeliveryFee());
        return ResponseEntity.ok(body);
    }

    @PostMapping("/v2/{productId}/quantity/{quantity}")
    public ResponseEntity<String> singleOrderV2(@PathVariable(name = "productId") String productId,
                                                @PathVariable(name = "quantity") String quantity
    ) {
        Cart cart = Cart.of(productId, quantity);
        OrderResponse response = orderService.createOrderOf(cart);
        String body = """
                OrderId => %s
                Total Price => %s
                Order Delivery Fee => %s
                Total Price With Delivery Fee => %s
                OrderItemResponses => %s
                """.formatted(response.orderId(), response.formattedTotalPrice(),
                response.formattedDeliveryFee(), response.formattedTotalPriceWithDeliveryFee(),
                response.orderItemResponses()
        );
        return ResponseEntity.ok(body);
    }
}
