package kr.co.system.homework.legacy.order.v2.domain;

import jakarta.persistence.*;
import kr.co.system.homework.legacy.product.v2.domain.ProductInfoV2;
import kr.co.system.homework.legacy.product.v2.domain.ProductV2;
import lombok.Getter;


@Getter
@Entity
@Table(name = "order_item_v2")
public class OrderItemV2 {

    @Id
    @Column(name = "order_item_v2_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_v2_id")
    private ProductV2 product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_v2_id")
    private OrderV2 orderV2;

    private int quantity;
    @Embedded
    private ProductInfoV2 productInfo;

    protected OrderItemV2() {

    }

    public static OrderItemV2 of(ProductV2 product, int quantity, ProductInfoV2 productInfo) {
        OrderItemV2 orderItem = new OrderItemV2();
        orderItem.product = product;
        orderItem.quantity = quantity;
        orderItem.productInfo = productInfo;
        return orderItem;
    }

    public void changeOrder(OrderV2 order) {
        this.orderV2 = order;
    }
}
