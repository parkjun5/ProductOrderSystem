package kr.co._39cm.homework.order.v1.domain;

import jakarta.persistence.*;
import kr.co._39cm.homework.product.v1.domain.ProductInfo;
import lombok.Getter;
import kr.co._39cm.homework.product.v1.domain.Product;

@Getter
@Entity
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;
    @Embedded
    private ProductInfo productInfo;

    protected OrderItem() {

    }

    public OrderItem(Product product, int quantity, ProductInfo productInfo) {
        this.product = product;
        this.quantity = quantity;
        this.productInfo = productInfo;
    }

    public void changeOrder(Order order) {
        this.order = order;
    }
}
