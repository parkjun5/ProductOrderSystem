package kr.co._39cm.homework.legacy.product.v1.domain;

import java.util.List;

public interface BatchProductRepository {
    void batchInsert(List<ProductV1> product, int batchSize);
}
