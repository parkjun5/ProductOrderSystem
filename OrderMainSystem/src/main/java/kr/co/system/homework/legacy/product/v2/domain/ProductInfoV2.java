package kr.co.system.homework.legacy.product.v2.domain;

import jakarta.persistence.Embeddable;
import kr.co.system.homework.support.ui.ValueObject;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Embeddable
public class ProductInfoV2 extends ValueObject<ProductInfoV2> {
    private String productName;
    private BigDecimal price;

    protected ProductInfoV2() {

    }

    public ProductInfoV2(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
    }
}
