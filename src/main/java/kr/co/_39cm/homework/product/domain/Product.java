package kr.co._39cm.homework.product.domain;

import jakarta.persistence.*;
import kr.co._39cm.homework.support.exception.SoldOutException;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
public class Product {

    @Id
    @Column(name = "product_id")
    private Long id;

    @Embedded
    private ProductInfo productInfo;
    private int stock;

    protected Product() {
    }

    public static Product of(long id, String name, BigDecimal price, int stock) {
        Product product = new Product();
        product.id = id;
        product.productInfo = new ProductInfo(name, price);
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
