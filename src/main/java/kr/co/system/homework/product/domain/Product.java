package kr.co.system.homework.product.domain;

import jakarta.persistence.*;
import kr.co.system.homework.support.domain.AggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "product")
public class Product extends AggregateRoot<Product, Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Version
    private Long version = 0L;

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

    public boolean hasNotEnoughStock(int orderedQuantity, int newOrderQuantity) {
        return this.stock < (orderedQuantity + newOrderQuantity);
    }

}
