package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.product.domain.Product;

public class OrderItemFixture {

    public static OrderItem orderItem(Product product, int quantity) {
        return new OrderItem(product, quantity, product.getProductInfo());
    }
}
