package kr.co._39cm.homework.product;

import kr.co._39cm.homework.product.v2.domain.ProductV2;

import java.math.BigDecimal;

public class ProductV2Fixture {

    public static ProductV2 productV2(String listenProductId, String listenQuantity) {
        return ProductV2.of(Long.parseLong(listenProductId), "name", BigDecimal.ONE, Integer.parseInt(listenQuantity));
    }

    public static ProductV2 productV2() {
        return productV2("10");
    }

    public static ProductV2 productV2(String quantity) {
        return productV2("1", quantity);
    }

}
