package kr.co.system.homework.legacy.order.v1.domain;

public interface OrderV1Repository {
    OrderV1 save(OrderV1 orderV1);
    void deleteAll();
}
