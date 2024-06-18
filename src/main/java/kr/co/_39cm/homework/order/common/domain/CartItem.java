package kr.co._39cm.homework.order.common.domain;

import lombok.Getter;

@Getter
public class CartItem {

    private Long productId;
    private int quantity;

    protected CartItem() { }

    public CartItem(String orderedProductId, String orderedQuantity) {

        try {
            this.productId = Long.parseLong(orderedProductId);
            this.quantity = Integer.parseInt((orderedQuantity));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("잘못된 값이 입력되었습니다. orderedProductId = " + orderedProductId + ", orderedQuantity = " + orderedQuantity);
        }

    }

    public void addQuantity(String orderedQuantity) {

        try {
            this.quantity = Integer.parseInt((orderedQuantity));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("잘못된 값이 입력되었습니다. orderedQuantity = " + orderedQuantity);
        }

        this.quantity += quantity;
    }
}
