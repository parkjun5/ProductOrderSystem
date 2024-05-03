package kr.co._39cm.homework.product.domain;

import java.util.List;

public interface BatchProductRepository {
    void batchInsert(List<Product> product, int batchSize);
}
