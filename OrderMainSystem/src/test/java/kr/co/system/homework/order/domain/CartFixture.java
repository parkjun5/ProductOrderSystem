package kr.co.system.homework.order.domain;

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
