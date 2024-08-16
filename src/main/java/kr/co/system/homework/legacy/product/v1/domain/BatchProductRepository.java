package kr.co.system.homework.legacy.product.v1.domain;

import java.util.List;

public interface BatchProductRepository {
    void batchInsert(List<ProductV1> product, int batchSize);
}
