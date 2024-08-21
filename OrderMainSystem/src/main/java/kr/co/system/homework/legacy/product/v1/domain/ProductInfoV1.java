package kr.co.system.homework.legacy.product.v1.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Embeddable
public class ProductInfoV1 {
    private String productName;
    private BigDecimal price;

    protected ProductInfoV1() {

    }

    public ProductInfoV1(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
    }
}
