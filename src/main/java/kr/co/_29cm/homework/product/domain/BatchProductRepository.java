package kr.co._29cm.homework.product.domain;

import java.util.List;

public interface BatchProductRepository {
    void batchInsert(List<Product> product, int batchSize);
}
