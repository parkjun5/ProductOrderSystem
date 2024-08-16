package kr.co.system.homework.product.domain;

import jakarta.persistence.Embeddable;
import kr.co.system.homework.support.ui.ValueObject;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Embeddable
public class ProductInfo extends ValueObject<ProductInfo> {
    private String productName;
    private BigDecimal price;

    protected ProductInfo() {

    }

    public ProductInfo(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
    }
}
