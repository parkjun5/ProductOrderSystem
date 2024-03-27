package kr.co._29cm.homework.order.application;

import kr.co._29cm.homework.order.domain.Cart;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.ui.OrderPrinter;
import kr.co._29cm.homework.product.application.ProductReader;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.ui.ProductPrinter;
import kr.co._29cm.homework.support.exception.SoldOutException;
import kr.co._29cm.homework.support.ui.CommandInputHandler;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class Cashier {
    private final ProductService productService;
    private final ProductReader productReader;
    private final OrderReceiver orderReceiver;
    private final OrderService orderService;

    public Cashier(ProductService productService,
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
            Order order = orderService.createOrderOf(cart);
            OrderPrinter.printResult(order);
        } catch (SoldOutException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchElementException | IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
