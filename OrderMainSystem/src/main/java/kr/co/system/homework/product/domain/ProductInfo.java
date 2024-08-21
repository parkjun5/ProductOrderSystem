package kr.co.system.homework.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.system.homework.support.ui.ValueObject;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Embeddable
public class ProductInfo extends ValueObject<ProductInfo> {

    private String productName;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private ProductStatus productStatus;

    protected ProductInfo() {

    }

    public ProductInfo(String productName, BigDecimal price) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("productName is null or empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price is null or negative");
        }

        this.productName = productName;
        this.price = price;
        this.productStatus = ProductStatus.DRAFT;
    }

    public void preparing() {
        this.productStatus = ProductStatus.PREPARING;
    }

    public void selling() {
        this.productStatus = ProductStatus.SELLING;
    }

    public void soldOut() {
        this.productStatus = ProductStatus.SOLD_OUT;
    }
}
