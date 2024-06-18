package kr.co._39cm.homework.order.common.application;

import kr.co._39cm.homework.order.common.domain.Cart;
import kr.co._39cm.homework.order.v1.domain.Order;
import kr.co._39cm.homework.order.common.ui.OrderPrinter;
import kr.co._39cm.homework.order.v1.application.OrderService;
import kr.co._39cm.homework.product.v1.application.ProductService;
import kr.co._39cm.homework.product.common.ui.ProductPrinter;
import kr.co._39cm.homework.product.v2.application.ProductV2Reader;
import kr.co._39cm.homework.support.exception.SoldOutException;
import kr.co._39cm.homework.support.ui.CommandInputHandler;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class Cashier {
    private final ProductService productService;
    private final ProductV2Reader productReader;
    private final OrderReceiver orderReceiver;
    private final OrderService orderService;

    public Cashier(ProductService productService,
                   ProductV2Reader productReader,
                   OrderReceiver orderReceiver,
                   OrderService orderService
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
            Order order = orderService.createOrderOf(cart);
            OrderPrinter.printResult(order);
        } catch (SoldOutException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchElementException | IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
