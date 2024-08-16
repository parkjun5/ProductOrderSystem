package kr.co._39cm.homework.order.domain;

public interface OrderV2Repository {
    OrderV2 save(OrderV2 order);
    void deleteAll();
}
