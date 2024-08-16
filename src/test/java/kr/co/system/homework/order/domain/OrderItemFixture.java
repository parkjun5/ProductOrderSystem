package kr.co.system.homework.order.domain;

import kr.co.system.homework.legacy.order.v1.domain.OrderItemV1;
import kr.co.system.homework.legacy.product.v1.domain.ProductV1;

public class OrderItemFixture {

    public static OrderItemV1 orderItem(ProductV1 product, int quantity) {
        return new OrderItemV1(product, quantity, product.getProductInfo());
    }
}
