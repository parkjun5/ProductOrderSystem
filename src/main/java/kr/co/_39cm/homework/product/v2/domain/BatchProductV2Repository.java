package kr.co._39cm.homework.product.v2.domain;

import java.util.List;

public interface BatchProductV2Repository {
    void batchInsert(List<ProductV2> product, int batchSize);
}
