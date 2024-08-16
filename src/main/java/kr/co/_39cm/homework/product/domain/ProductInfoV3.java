package kr.co._39cm.homework.product.domain;

import jakarta.persistence.Embeddable;
import kr.co._39cm.homework.support.ui.ValueObject;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Embeddable
public class ProductInfoV3 extends ValueObject<ProductInfoV3> {
    private String productName;
    private BigDecimal price;

    protected ProductInfoV3() {

    }

    public ProductInfoV3(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
    }
}
