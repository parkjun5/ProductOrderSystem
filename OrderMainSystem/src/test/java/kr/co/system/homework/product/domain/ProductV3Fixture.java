package kr.co.system.homework.product.domain;

import java.math.BigDecimal;

public class ProductV3Fixture {

    public static Product product(String listenProductId, String listenQuantity) {
        return Product.builder("name", BigDecimal.ONE)
                .id(Long.parseLong(listenProductId))
                .stock(Integer.parseInt(listenQuantity))
                .build();
    }

    public static Product product() {
        return product("10");
    }

    public static Product product(String quantity) {
        return product("1", quantity);
    }

}
