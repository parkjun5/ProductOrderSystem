package kr.co._39cm.homework.legacy.order.v1.domain;

public interface OrderV1Repository {
    OrderV1 save(OrderV1 orderV1);
    void deleteAll();
}
