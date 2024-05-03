package kr.co._39cm.homework.order.v2.application;

import kr.co._39cm.homework.order.v2.domain.OrderV2;
import kr.co._39cm.homework.order.v2.ui.OrderResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderEvent2(OrderV2 event) {
        OrderResponse response = event.toResponse();
        System.out.println("AFTER_COMMIT Order ID: " + response);
    }

}