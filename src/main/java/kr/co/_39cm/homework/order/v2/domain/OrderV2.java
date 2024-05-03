package kr.co._39cm.homework.order.v2.domain;

import jakarta.persistence.*;
import kr.co._39cm.homework.order.v2.ui.OrderItemResponse;
import kr.co._39cm.homework.order.v2.ui.OrderResponse;
import kr.co._39cm.homework.support.domain.AggregateRoot;
import lombok.Getter;
import org.hibernate.annotations.JavaType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Getter
public class OrderV2 extends AggregateRoot<OrderV2, OrderId> {

    @Id @GeneratedValue(strategy = IDENTITY)
    @JavaType(OrderId.OrderIdJavaType.class)
    private OrderId id;

    @Version
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_item_to_order")
    )
    private List<OrderItemV2> orderItems = new ArrayList<>();

    private BigDecimal totalPrice;

    @Transient
    private final DecimalFormat formatter = new DecimalFormat("#,##0");

    protected OrderV2() {

    }

//    @Override
//    protected OrderV2 publishEvent() {
//        return this;
//    }

    @Override
    public OrderId getId() {
        return this.id;
    }

    public OrderV2(List<OrderItemV2> orderItems) {
        this.orderItems = orderItems;
        this.totalPrice = orderItems.stream()
                .map(item ->
                        new BigDecimal(item.getQuantity()).multiply(item.getProductInfo()
                                .getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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


    public OrderResponse toResponse() {
        var orderItemResponses = orderItems.stream()
                                           .map(it -> new OrderItemResponse(it.getProduct().getId().longValue(), it.getQuantity()))
                                           .toList();
        return new OrderResponse(this.id.longValue(), getFormattedTotalPrice(),
                getFormattedDeliveryFee(), getFormattedTotalPriceWithDeliveryFee(),
                orderItemResponses);
    }

}
