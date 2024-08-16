package kr.co.system.homework.product.domain;

import java.util.List;

public interface BatchProductRepository {
    void batchInsert(List<Product> product, int batchSize);
}
