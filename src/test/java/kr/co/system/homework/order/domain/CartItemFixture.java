package kr.co.system.homework.order.domain;

public class CartItemFixture {

    public static CartItem cartItem(String orderedProductId) {
        return cartItem(orderedProductId, "1");
    }

    public static CartItem cartItem(String orderedProductId, String quantity) {
        return new CartItem(orderedProductId, quantity);
    }
}
