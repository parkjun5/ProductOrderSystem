package kr.co._39cm.homework.order.ui;

import kr.co._39cm.homework.legacy.order.v1.domain.OrderV1;
import kr.co._39cm.homework.legacy.order.v1.domain.OrderItemV1;
import kr.co._39cm.homework.legacy.product.v1.domain.ProductInfoV1;

public class OrderPrinter {

    private OrderPrinter() {

    }
    private static final String BLOCK = "---------------------------------------";

    public static void printResult(OrderV1 order) {
        System.out.println("주문내역:");
        System.out.println(BLOCK);
        for (OrderItemV1 orderItem : order.getOrderItems()) {
            ProductInfoV1 productInfo = orderItem.getProductInfo();
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
