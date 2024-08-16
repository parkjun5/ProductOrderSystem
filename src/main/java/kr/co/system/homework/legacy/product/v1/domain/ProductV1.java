package kr.co.system.homework.legacy.product.v1.domain;

import jakarta.persistence.*;
import kr.co.system.homework.support.exception.SoldOutException;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
public class ProductV1 {

    @Id
    @Column(name = "product_v1_id")
    private Long id;

    @Embedded
    private ProductInfoV1 productInfo;
    private int stock;

    protected ProductV1() {
    }

    public static ProductV1 of(long id, String name, BigDecimal price, int stock) {
        ProductV1 product = new ProductV1();
        product.id = id;
        product.productInfo = new ProductInfoV1(name, price);
        product.stock = stock;

        return product;
    }

    public boolean hasNotEnoughStock(int orderQuantity) {
        return this.stock < orderQuantity;
    }

    public int deductedStock(int quantity) {
        int deductedStock = this.stock - quantity;
        if (deductedStock < 0) {
            throw new SoldOutException();
        }
        return deductedStock;
    }

    public void changeStock(int deductedStock) {
        this.stock = deductedStock;
    }
}
