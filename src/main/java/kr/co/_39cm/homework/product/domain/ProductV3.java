package kr.co._39cm.homework.product.domain;

import jakarta.persistence.*;
import kr.co._39cm.homework.support.domain.AggregateRoot;
import kr.co._39cm.homework.support.exception.SoldOutException;
import lombok.Getter;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "product_v3")
public class ProductV3 extends AggregateRoot<ProductV3, Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Version
    private Long version = 0L;

    @Embedded
    private ProductInfoV3 productInfo;
    private int stock;
    protected ProductV3() {
    }

    public static ProductV3 of(String name, BigDecimal price, int stock) {
        ProductV3 product = new ProductV3();
        product.productInfo = new ProductInfoV3(name, price);
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
