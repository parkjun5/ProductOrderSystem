package kr.co._39cm.homework.product.domain;

import java.util.List;

public interface BatchProductV3Repository {
    void batchInsert(List<ProductV3> product, int batchSize);
}
