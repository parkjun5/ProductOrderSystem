package kr.co._39cm.homework.order.common.application;

import kr.co._39cm.homework.order.common.domain.Cart;
import kr.co._39cm.homework.order.common.ui.OrderInputHandlerInterface;
import org.springframework.stereotype.Component;

@Component
public class OrderReceiver {

    private final OrderInputHandlerInterface orderInputHandlerInterface;

    public OrderReceiver(OrderInputHandlerInterface orderInputHandlerInterface) {
        this.orderInputHandlerInterface = orderInputHandlerInterface;
    }

    public Cart collectRequestToCart() {

        Cart cart = new Cart();
        String endRequest = " ";

        while (true) {
            String orderedProductId = orderInputHandlerInterface.listenProductId();
            String orderedQuantity = orderInputHandlerInterface.listenQuantity();

            if (endRequest.equals(orderedProductId) && endRequest.equals(orderedQuantity)) {
                break;
            }

            cart.putItem(orderedProductId, orderedQuantity);
        }

        return cart;
    }
}
