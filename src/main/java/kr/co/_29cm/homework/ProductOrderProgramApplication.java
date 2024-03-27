package kr.co._29cm.homework;

import kr.co._29cm.homework.order.application.Cashier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProductOrderProgramApplication {

    private static final String BIG_INPUT_DATA_LOCATION = "src/main/resources/big_items.csv";

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ProductOrderProgramApplication.class, args);

        Cashier cashier = ctx.getBean(Cashier.class);

        cashier.inputInitData(BIG_INPUT_DATA_LOCATION);
        cashier.activate();
    }

}
