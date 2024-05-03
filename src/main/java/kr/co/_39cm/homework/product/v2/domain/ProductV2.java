package kr.co._39cm.homework.product.v2.domain;

import jakarta.persistence.*;
import kr.co._39cm.homework.support.domain.AggregateRoot;
import kr.co._39cm.homework.support.exception.SoldOutException;
import lombok.Getter;
import org.hibernate.annotations.JavaType;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "product_v2")
public class ProductV2 extends AggregateRoot<ProductV2, ProductId> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JavaType(ProductId.ProductIdJavaType.class)
    @Column(name = "product_id")
    private ProductId id;

    @Version
    private Long version = 0L;
    @Embedded
    private ProductInfoV2 productInfo;
    private int stock;

    protected ProductV2() {
    }

    public static ProductV2 of(long id, String name, BigDecimal price, int stock) {
        ProductV2 product = new ProductV2();
        product.id = new ProductId(id);
        product.productInfo = new ProductInfoV2(name, price);
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
