package kr.co._39cm.homework;

import kr.co._39cm.homework.order.common.application.Cashier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class ProductOrderProgramApplication {

    public static final String BIG_INPUT_DATA_LOCATION = "src/main/resources/big_items.csv";

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ProductOrderProgramApplication.class, args);

        Cashier cashier = ctx.getBean(Cashier.class);

        cashier.inputInitData(BIG_INPUT_DATA_LOCATION);
    }

}
