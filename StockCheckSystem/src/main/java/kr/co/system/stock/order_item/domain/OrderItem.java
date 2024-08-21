package kr.co.system.stock.order_item.domain;

import jakarta.persistence.*;
import kr.co.system.stock.product.Product;
import kr.co.system.stock.support.DomainEntity;
import kr.co.system.stock.support.NotEnoughStockInProduct;
import lombok.Getter;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class OrderItem extends DomainEntity<OrderItem, Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    private int selectedQuantity;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus orderItemStatus = OrderItemStatus.ORDERED;

    protected OrderItem() {

    }

    public void accepted() {
        this.orderItemStatus = OrderItemStatus.ACCEPTED;
    }

    public void canceled() {
        this.orderItemStatus = OrderItemStatus.CANCELED;
    }

    public void delivering() {
        this.orderItemStatus = OrderItemStatus.DELIVERING;
    }

    public void delivered() {
        this.orderItemStatus = OrderItemStatus.DELIVERED;
    }

    public void completed() {
        this.orderItemStatus = OrderItemStatus.COMPLETED;
    }

    public void updateOrderItemStatusBasedOnStock(int alreadyOrderedQuantity) {
        try {
            product.ensureOrderStock(this.selectedQuantity, alreadyOrderedQuantity);
            this.accepted();
        } catch (NotEnoughStockInProduct ex) {
            this.canceled();
        }
    }

    public long getProductId() {
        return this.product.getId();
    }
}
