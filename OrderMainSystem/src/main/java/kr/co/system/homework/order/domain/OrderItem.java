package kr.co.system.homework.order.domain;

import jakarta.persistence.*;
import kr.co.system.homework.product.application.exception.NotEnoughStockInProduct;
import kr.co.system.homework.product.domain.Product;
import kr.co.system.homework.support.domain.DomainEntity;
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

    public static OrderItem of(Product product, int selectedQuantity) {
        if (product == null) {
            throw new IllegalArgumentException("product는 비어있을 수 없습니다.");
        }
        if (selectedQuantity == 0) {
            throw new IllegalArgumentException("Item의 수량은 0 일 수 없습니다");
        }

        OrderItem orderItem = new OrderItem();
        orderItem.selectedQuantity = selectedQuantity;
        orderItem.product = product;
        return orderItem;
    }

    public BigDecimal getTotalItemsPrice() {
        return this.product.getProductInfo().getPrice().multiply(new BigDecimal(selectedQuantity));
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
