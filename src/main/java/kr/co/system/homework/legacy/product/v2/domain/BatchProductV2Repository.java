package kr.co.system.homework.legacy.product.v2.domain;

import java.util.List;

public interface BatchProductV2Repository {
    void batchInsert(List<ProductV2> product, int batchSize);
}
