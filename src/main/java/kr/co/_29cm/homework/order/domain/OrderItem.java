package kr.co._29cm.homework.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductInfo;

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
