package kr.co._39cm.homework.order.domain;

import kr.co._39cm.homework.legacy.order.v1.domain.OrderItemV1;
import kr.co._39cm.homework.legacy.product.v1.domain.ProductV1;

public class OrderItemFixture {

    public static OrderItemV1 orderItem(ProductV1 product, int quantity) {
        return new OrderItemV1(product, quantity, product.getProductInfo());
    }
}
