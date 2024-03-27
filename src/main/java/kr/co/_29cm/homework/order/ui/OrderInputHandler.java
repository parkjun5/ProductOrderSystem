package kr.co._29cm.homework.order.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class OrderInputHandler implements OrderInputHandlerInterface {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String listenProductId() {
        System.out.print("상품번호 : ");
        return scanner.nextLine();
    }
    @Override
    public String listenQuantity() {
        System.out.print("수량 : ");
        return scanner.nextLine();
    }

}
