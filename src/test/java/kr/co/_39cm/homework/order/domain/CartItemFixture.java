package kr.co._39cm.homework.order.domain;

import kr.co._39cm.homework.order.common.domain.CartItem;

public class CartItemFixture {

    public static CartItem cartItem(String orderedProductId) {
        return cartItem(orderedProductId, "1");
    }

    public static CartItem cartItem(String orderedProductId, String quantity) {
        return new CartItem(orderedProductId, quantity);
    }
}
