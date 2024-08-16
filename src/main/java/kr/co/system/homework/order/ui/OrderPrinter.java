package kr.co.system.homework.order.ui;

import kr.co.system.homework.order.ui.dto.OrderItemResponse;
import kr.co.system.homework.order.ui.dto.OrderResponse;

public class OrderPrinter {

    private OrderPrinter() {

    }

    private static final String BLOCK = "---------------------------------------";

    public static void printResult(OrderResponse orderResponse) {
        System.out.println("주문내역:");
        System.out.println(BLOCK);
        for (OrderItemResponse orderItemResponse : orderResponse.orderItemResponses()) {
            System.out.println(orderItemResponse.productName() + " - " + orderItemResponse.quantity() + "개");
        }
        System.out.println(BLOCK);
        System.out.println("주문금액: " + orderResponse.formattedTotalPrice() + "원");
        if (orderResponse.hasDeliveryFee()) {
            System.out.println("배송비: " + orderResponse.formattedDeliveryFee() + "원");
        }
        System.out.println(BLOCK);
        System.out.println("지불금액: " + orderResponse.formattedTotalPriceWithDeliveryFee() + "원");
        System.out.println(BLOCK + "\n");
    }
}
