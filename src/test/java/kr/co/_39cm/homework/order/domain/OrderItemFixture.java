package kr.co._39cm.homework.order.domain;

import kr.co._39cm.homework.product.domain.Product;

public class OrderItemFixture {

    public static OrderItem orderItem(Product product, int quantity) {
        return new OrderItem(product, quantity, product.getProductInfo());
    }
}
