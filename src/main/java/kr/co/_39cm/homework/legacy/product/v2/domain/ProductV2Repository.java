package kr.co._39cm.homework.legacy.product.v2.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductV2Repository {

    Optional<ProductV2> findById(Long productId);
    List<ProductV2> findProductsInStock();
    Page<ProductV2> findProductsInStockWithPage(Pageable pageable);
    void batchInsert(List<ProductV2> product, int batchSize);
    ProductV2 save(ProductV2 product);
    List<ProductV2> findAll();
    void deleteAll();
    Optional<ProductV2> findByIdWithOptimisticLock(Long productId);
    void updateProductStock(Long id, int stock, int deductedStock);
}
