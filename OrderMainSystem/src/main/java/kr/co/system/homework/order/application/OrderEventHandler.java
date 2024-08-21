package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventHandler {

    @Async
    @TransactionalEventListener
    public void handleOrderEvent(Order order) {
        order.getOrderItems().forEach(orderItem -> {
            // TODO: Kafka or rabbitMq publish Message
        });
    }

}
