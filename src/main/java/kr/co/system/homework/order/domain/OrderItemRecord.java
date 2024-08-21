package kr.co.system.homework.order.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class OrderItemRecord {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_item_record_id")
    private Long id;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "product_id")
    private Long productId;

    private int orderedQuantity;

    @Version
    private Long version = 0L;

    protected OrderItemRecord() {

    }

    public static OrderItemRecord of(long orderItemId, long productId, int selectedQuantity) {
        OrderItemRecord orderItemRecord = new OrderItemRecord();
        orderItemRecord.orderItemId = orderItemId;
        orderItemRecord.productId = productId;
        orderItemRecord.orderedQuantity = selectedQuantity;
        return orderItemRecord;
    }
}
