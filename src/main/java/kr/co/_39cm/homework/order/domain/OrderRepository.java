package kr.co._39cm.homework.order.domain;

public interface OrderRepository {
    Order save(Order order);
    void deleteAll();
}
