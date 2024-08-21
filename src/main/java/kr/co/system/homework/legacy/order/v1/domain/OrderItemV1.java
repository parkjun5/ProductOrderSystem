package kr.co.system.homework.legacy.order.v1.domain;

import jakarta.persistence.*;
import kr.co.system.homework.legacy.product.v1.domain.ProductInfoV1;
import lombok.Getter;
import kr.co.system.homework.legacy.product.v1.domain.ProductV1;

@Getter
@Entity
@Table(name = "order_item_v1")
public class OrderItemV1 {

    @Id
    @Column(name = "order_item_v1_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private ProductV1 productV1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_v1_id")
    private OrderV1 orderV1;

    private int quantity;
    @Embedded
    private ProductInfoV1 productInfo;

    protected OrderItemV1() {

    }

    public OrderItemV1(ProductV1 productV1, int quantity, ProductInfoV1 productInfo) {
        this.productV1 = productV1;
        this.quantity = quantity;
        this.productInfo = productInfo;
    }

    public void changeOrder(OrderV1 orderV1) {
        this.orderV1 = orderV1;
    }
}
