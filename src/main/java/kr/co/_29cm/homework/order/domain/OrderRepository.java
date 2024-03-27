package kr.co._29cm.homework.order.domain;

public interface OrderRepository {
    Order save(Order order);
    void deleteAll();
}
