package kr.co._39cm.homework.order.ui;

import kr.co._39cm.homework.legacy.order.v1.application.OrderV1Service;
import kr.co._39cm.homework.order.domain.Cart;
import kr.co._39cm.homework.legacy.order.v1.domain.OrderV1;
import kr.co._39cm.homework.order.application.OrderV2Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderV1Service orderService;
    private final OrderV2Service orderV2Service;

    public OrderController(OrderV1Service orderService, OrderV2Service orderV2Service) {
        this.orderService = orderService;
        this.orderV2Service = orderV2Service;
    }

    @PostMapping("/{productId}/quantity/{quantity}")
    public ResponseEntity<String> singleOrder(@PathVariable(name = "productId") String productId,
                                              @PathVariable(name = "quantity") String quantity
    ) {
        Cart cart = Cart.of(productId, quantity);
        OrderV1 order = orderService.createOrderOf(cart);
        String body = """
                OrderId => %s
                Order Delivery Fee => %s
                """.formatted(order.getId(), order.getFormattedTotalPriceWithDeliveryFee());
        return ResponseEntity.ok(body);
    }

    @PostMapping("/v2/{productId}/quantity/{quantity}")
    public ResponseEntity<String> singleOrderV2(@PathVariable(name = "productId") String productId,
                                                @PathVariable(name = "quantity") String quantity
    ) {
        Cart cart = Cart.of(productId, quantity);
        OrderResponse response = orderV2Service.createOrderOf(cart);
        String body = """
                OrderId => %s
                Total Price => %s
                Order Delivery Fee => %s
                Total Price With Delivery Fee => %s
                OrderItemResponses => %s
                """.formatted(response.orderId(), response.totalPrice(),
                response.deliveryFee(), response.totalPriceWithDeliveryFee(),
                response.orderItemResponses()
                );
        return ResponseEntity.ok(body);
    }
}
