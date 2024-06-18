package kr.co._39cm.homework.order.domain;

import kr.co._39cm.homework.order.common.domain.Cart;

public class CartFixture {
    public static Cart cart(String productId, String quantity) {
        Cart cart = new Cart();
        cart.putItem(productId, quantity);
        return cart;
    }

    public static Cart cart(String productId) {
        Cart cart = new Cart();
        cart.putItem(productId, "1");
        return cart;
    }

    public static Cart cart() {
        return new Cart();
    }

}
