package kr.co._29cm.homework.order.ui;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderItem;
import kr.co._29cm.homework.product.domain.ProductInfo;

public class OrderPrinter {

    private OrderPrinter() {

    }
    private static final String BLOCK = "---------------------------------------";

    public static void printResult(Order order) {
        System.out.println("주문내역:");
        System.out.println(BLOCK);
        for (OrderItem orderItem : order.getOrderItems()) {
            ProductInfo productInfo = orderItem.getProductInfo();
            System.out.println(productInfo.getProductName() + " - " + orderItem.getQuantity() + "개");
        }
        System.out.println(BLOCK);
        System.out.println("주문금액: " + order.getFormattedTotalPrice() + "원");
        if (order.hasDeliveryFee()) {
            System.out.println("배송비: " + order. getFormattedDeliveryFee() + "원");
        }
        System.out.println(BLOCK);
        System.out.println("지불금액: " + order.getFormattedTotalPriceWithDeliveryFee() + "원");
        System.out.println(BLOCK + "\n");
    }
}
