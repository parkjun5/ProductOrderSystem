package kr.co.system.homework.order.domain;

import jakarta.persistence.*;
import kr.co.system.homework.support.domain.AggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Getter
@Table(name = "orders")
public class Order extends AggregateRoot<Order, Long> {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    @Version
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(
            name = "orders_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_item_to_order")
    )
    private List<OrderItem> orderItems = new ArrayList<>();

    private BigDecimal totalPrice;

    private LocalDateTime orderTime;

    @Transient
    private final DecimalFormat formatter = new DecimalFormat("#,##0");

    protected Order() {

    }

    public static Order of(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("orderItems is null or empty");
        }

        Order order = new Order();
        order.orderItems = orderItems;
        order.totalPrice = orderItems.stream().map(OrderItem::getTotalItemsPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.orderTime = LocalDateTime.now();
        return order;
    }

    public boolean hasDeliveryFee() {
        return !getDeliveryFee().equals(BigDecimal.ZERO);
    }

    public String getFormattedTotalPrice() {
        return formatter.format(totalPrice);
    }

    public String getFormattedDeliveryFee() {
        return formatter.format(getDeliveryFee());
    }

    public String getFormattedTotalPriceWithDeliveryFee() {
        return formatter.format(totalPrice.add(getDeliveryFee()));
    }

    private BigDecimal getDeliveryFee() {
        BigDecimal deliveryFee = new BigDecimal("2500");
        BigDecimal threshold = new BigDecimal("50000");

        if (totalPrice.compareTo(threshold) < 0) {
            return deliveryFee;
        }

        return BigDecimal.ZERO;
    }

    public void registerEvent() {
        this.registerEvent(this);
    }

}
