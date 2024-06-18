package kr.co._39cm.homework.order.v1.domain;

public interface OrderRepository {
    Order save(Order order);
    void deleteAll();
}
