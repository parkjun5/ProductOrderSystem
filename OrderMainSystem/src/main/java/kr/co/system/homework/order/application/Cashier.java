package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.order.ui.OrderPrinter;
import kr.co.system.homework.order.ui.dto.OrderResponse;
import kr.co.system.homework.product.application.ProductReader;
import kr.co.system.homework.product.application.ProductService;
import kr.co.system.homework.product.ui.ProductPrinter;
import kr.co.system.homework.support.exception.SoldOutException;
import kr.co.system.homework.support.ui.CommandInputHandler;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class Cashier {

    private final OrderService orderService;
    private final ProductReader productReader;
    private final OrderReceiver orderReceiver;
    private final ProductService productService;

    public Cashier(
            OrderService orderService,
            ProductReader productReader,
            OrderReceiver orderReceiver,
            ProductService productService
    ) {
        this.orderService = orderService;
        this.productReader = productReader;
        this.orderReceiver = orderReceiver;
        this.productService = productService;
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
            OrderResponse orderResponse = orderService.createOrderBy(cart);
            OrderPrinter.printResult(orderResponse);
        } catch (SoldOutException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchElementException | IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
