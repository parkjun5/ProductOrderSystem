package kr.co._39cm.homework.product.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Embeddable
public class ProductInfo {
    private String productName;
    private BigDecimal price;

    protected ProductInfo() {

    }

    public ProductInfo(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
    }
}
