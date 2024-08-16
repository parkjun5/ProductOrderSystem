package kr.co._39cm.homework.order.domain;

import jakarta.persistence.*;
import kr.co._39cm.homework.order.ui.OrderItemEvent;
import kr.co._39cm.homework.legacy.product.v2.domain.ProductInfoV2;
import kr.co._39cm.homework.legacy.product.v2.domain.ProductV2;
import kr.co._39cm.homework.support.domain.DomainEntity;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class OrderItemV2 extends DomainEntity<OrderItemV2, Long> {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private ProductV2 product;

    private int quantity;
    @Embedded
    private ProductInfoV2 productInfo;

    protected OrderItemV2() {

    }

    public OrderItemV2(ProductV2 product, int quantity, ProductInfoV2 productInfo) {
        this.product = product;
        this.quantity = quantity;
        this.productInfo = productInfo;
    }

    public OrderItemEvent toEvent() {
        return new OrderItemEvent(this.product.getId().longValue(), quantity);
    }
}
