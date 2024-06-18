package kr.co._39cm.homework.product.v1.domain;

import java.util.List;

public interface BatchProductRepository {
    void batchInsert(List<Product> product, int batchSize);
}
