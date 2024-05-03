package kr.co._39cm.homework.order.v2.domain;

public interface OrderV2Repository {
    OrderV2 save(OrderV2 order);
    void deleteAll();
}
