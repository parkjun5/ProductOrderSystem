package kr.co._39cm.homework.order.application;

import kr.co._39cm.homework.order.domain.Cart;
import kr.co._39cm.homework.legacy.order.v1.domain.OrderV1;
import kr.co._39cm.homework.order.ui.OrderPrinter;
import kr.co._39cm.homework.legacy.order.v1.application.OrderV1Service;
import kr.co._39cm.homework.legacy.product.v1.application.ProductV1Service;
import kr.co._39cm.homework.product.ui.ProductPrinter;
import kr.co._39cm.homework.legacy.product.v2.application.ProductV2Reader;
import kr.co._39cm.homework.support.exception.SoldOutException;
import kr.co._39cm.homework.support.ui.CommandInputHandler;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class Cashier {
    private final ProductV1Service productService;
    private final ProductV2Reader productReader;
    private final OrderReceiver orderReceiver;
    private final OrderV1Service orderService;

    public Cashier(ProductV1Service productService,
                   ProductV2Reader productReader,
                   OrderReceiver orderReceiver,
                   OrderV1Service orderService
    ) {
        this.productService = productService;
        this.productReader = productReader;
        this.orderReceiver = orderReceiver;
        this.orderService = orderService;
    }

    public void inputInitData(String inputDataLocation) {
        productReader.addNewProductDataFrom(inputDataLocation);
    }

    public void activate() {

        CommandInputHandler commandInputHandler = new CommandInputHandler();

        boolean flag = true;

        while (flag) {
            switch (commandInputHandler.listenCommand()) {
                case "o", "order" -> orderStart();
                case "q", "quit" -> {
                    commandInputHandler.endProgram();
                    flag = false;
                }
                default -> commandInputHandler.tryAgain();
            }
        }

    }

    private void orderStart() {
        try {
            ProductPrinter.displayCurrentProducts(productService.getOrderedAvailableProducts());
            Cart cart = orderReceiver.collectRequestToCart();
            OrderV1 order = orderService.createOrderOf(cart);
            OrderPrinter.printResult(order);
        } catch (SoldOutException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchElementException | IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
