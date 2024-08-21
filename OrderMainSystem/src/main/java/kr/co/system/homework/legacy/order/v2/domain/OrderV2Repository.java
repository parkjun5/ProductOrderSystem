package kr.co.system.homework.legacy.order.v2.domain;

public interface OrderV2Repository {
    OrderV2 save(OrderV2 orderV2);
    void deleteAll();
}
