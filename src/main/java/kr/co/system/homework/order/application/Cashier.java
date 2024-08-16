package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.legacy.order.v1.domain.OrderV1;
import kr.co.system.homework.order.domain.Order;
import kr.co.system.homework.order.ui.OrderPrinter;
import kr.co.system.homework.legacy.order.v1.application.OrderV1Service;
import kr.co.system.homework.legacy.product.v1.application.ProductV1Service;
import kr.co.system.homework.order.ui.dto.OrderResponse;
import kr.co.system.homework.product.application.ProductReader;
import kr.co.system.homework.product.application.ProductService;
import kr.co.system.homework.product.ui.ProductPrinter;
import kr.co.system.homework.legacy.product.v2.application.ProductV2Reader;
import kr.co.system.homework.support.exception.SoldOutException;
import kr.co.system.homework.support.ui.CommandInputHandler;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class Cashier {
    private final ProductService productService;
    private final ProductReader productReader;
    private final OrderReceiver orderReceiver;
    private final OrderService orderService;

    public Cashier(
            ProductService productService,
            ProductReader productReader,
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
            OrderResponse orderResponse = orderService.createOrderOf(cart);
            OrderPrinter.printResult(orderResponse);
        } catch (SoldOutException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchElementException | IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
