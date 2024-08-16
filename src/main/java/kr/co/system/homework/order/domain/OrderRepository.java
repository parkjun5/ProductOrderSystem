package kr.co.system.homework.order.domain;

public interface OrderRepository {
    Order save(Order order);
    void deleteAll();
}
