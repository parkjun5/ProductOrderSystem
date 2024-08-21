package kr.co.system.stock.product;

import jakarta.persistence.*;
import kr.co.system.stock.support.AggregateRoot;
import kr.co.system.stock.support.NotEnoughStockInProduct;
import lombok.Getter;

import java.math.BigDecimal;

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

    public void ensureOrderStock(int newQuantity, int alreadyOrderedQuantity) {
        int newOrderStock = newQuantity + alreadyOrderedQuantity;
        if (this.stock < newOrderStock) {
            throw new NotEnoughStockInProduct(this.getId());
        }
    }

    public void changeStatusPreparing() {
        productInfo.preparing();
    }

    public void changeStatusSelling() {
        productInfo.selling();
    }

    public void changeStatusSoldOut() {
        productInfo.soldOut();
    }

    public static Builder builder(String name, BigDecimal price) {
        return new Builder(name, price);
    }

    public static class Builder {

        private final Product product = new Product();

        public Builder(String name, BigDecimal price) {
            product.productInfo = new ProductInfo(name, price);
        }

        public Builder id(Long id) {
            product.id = id;
            return this;
        }

        public Builder name(String name) {
            ProductInfo productInfo = product.productInfo;
            product.productInfo = new ProductInfo(name, productInfo.getPrice());
            return this;
        }

        public Builder price(BigDecimal price) {
            ProductInfo productInfo = product.productInfo;
            product.productInfo = new ProductInfo(productInfo.getProductName(), price);
            return this;
        }

        public Builder stock(int stock) {
            product.stock = stock;
            return this;
        }

        public Product build() {
            return product;
        }
    }

}
