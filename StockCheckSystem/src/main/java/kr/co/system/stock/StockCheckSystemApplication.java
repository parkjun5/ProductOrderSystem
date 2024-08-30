package kr.co.system.stock;

import kr.co.system.stock.order_item.application.OrderItemConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StockCheckSystemApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(StockCheckSystemApplication.class, args);

        OrderItemConsumer consumer = ctx.getBean(OrderItemConsumer.class);
        consumer.receiveOrderItem();

    }
}
