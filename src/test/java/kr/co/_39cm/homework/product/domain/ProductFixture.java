package kr.co._39cm.homework.product.domain;

import java.math.BigDecimal;

public class ProductFixture {

    public static Product product(String listenProductId, String listenQuantity) {
        return Product.of(Long.parseLong(listenProductId), "name", BigDecimal.ONE, Integer.parseInt(listenQuantity));
    }

    public static Product product() {
        return product("10");
    }

    public static Product product(String quantity) {
        return product("1", quantity);
    }

}
