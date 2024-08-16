package kr.co._39cm.homework.product.domain;

import kr.co._39cm.homework.legacy.product.v1.domain.ProductV1;

import java.math.BigDecimal;

public class ProductFixture {

    public static ProductV1 product(String listenProductId, String listenQuantity) {
        return ProductV1.of(Long.parseLong(listenProductId), "name", BigDecimal.ONE, Integer.parseInt(listenQuantity));
    }

    public static ProductV1 product() {
        return product("10");
    }

    public static ProductV1 product(String quantity) {
        return product("1", quantity);
    }

}
