package kr.co.system.homework;

import kr.co.system.homework.order.application.Cashier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class OrderMainSystemApplication {

    public static final String BIG_INPUT_DATA_LOCATION = "OrderMainSystem/src/main/resources/big_items.csv";

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(OrderMainSystemApplication.class, args);
        Cashier cashier = ctx.getBean(Cashier.class);

        cashier.inputInitData(BIG_INPUT_DATA_LOCATION);
        cashier.activate();
    }

}
