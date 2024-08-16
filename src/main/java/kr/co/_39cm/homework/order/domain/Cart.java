package kr.co._39cm.homework.order.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    Map<String, CartItem> cartMap = new HashMap<>();

    public static Cart of(String productId, String quantity) {
        Cart cart = new Cart();
        cart.putItem(productId, quantity);
        return cart;
    }

    public void putItem(String orderedProductId, String orderedQuantity) {
        boolean isBlankProductId = StringUtils.isBlank(orderedProductId);
        boolean isBlankQuantity = StringUtils.isBlank(orderedQuantity);
        if (isBlankProductId || isBlankQuantity) {
            throw new IllegalArgumentException("상품 번호와 주문 수량은 비어있을 수 없습니다. 상품번호 :" + orderedProductId + "수량 : " +orderedQuantity );
        }
        cartMap.compute(orderedProductId, (_key, cartItem) ->
                        createOrUpdateCart(cartItem, orderedProductId, orderedQuantity));
    }

    private CartItem createOrUpdateCart(CartItem existingCartItem,
                                        String orderedProductId,
                                        String orderedQuantity
    ) {
        if (existingCartItem == null) {
            return new CartItem(orderedProductId, orderedQuantity);
        }
        existingCartItem.addQuantity(orderedQuantity);
        return existingCartItem;
    }

    public List<CartItem> getCartItems() {
        if (cartMap.values().isEmpty()) {
            throw new IllegalArgumentException("카트가 비어 있습니다.");
        }
        return cartMap.values().stream().toList();
    }

}
