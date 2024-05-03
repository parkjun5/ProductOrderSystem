package kr.co._39cm.homework.order.v2.domain;

import jakarta.persistence.*;
import kr.co._39cm.homework.order.v2.ui.OrderItemEvent;
import kr.co._39cm.homework.product.v2.domain.ProductInfoV2;
import kr.co._39cm.homework.product.v2.domain.ProductV2;
import kr.co._39cm.homework.support.domain.DomainEntity;
import lombok.Getter;
import org.hibernate.annotations.JavaType;

import static jakarta.persistence.GenerationType.IDENTITY;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Getter
public class OrderItemV2 extends DomainEntity<OrderItemV2, OrderItemId> {
    @Id @GeneratedValue(strategy = IDENTITY)
    @JavaType(OrderItemId.OrderItemIdJavaType.class)
    private OrderItemId id;

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
