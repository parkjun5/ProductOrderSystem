package kr.co.system.homework.legacy.order.v2.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "orders_v2")
@Entity
public class OrderV2 {

    @Id
    @Column(name = "order_v2_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "orderV2", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<OrderItemV2> orderItems = new ArrayList<>();

    private BigDecimal totalPrice;

    protected OrderV2() {
    }

    @Transient
    private final DecimalFormat formatter = new DecimalFormat("#,##0");

    public OrderV2(List<OrderItemV2> orderItems) {
        this.orderItems = orderItems;
        for (OrderItemV2 orderItem : orderItems) {
            orderItem.changeOrder(this);
        }
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

}
