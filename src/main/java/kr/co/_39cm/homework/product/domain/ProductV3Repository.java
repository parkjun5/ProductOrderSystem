package kr.co._39cm.homework.product.domain;

import kr.co._39cm.homework.legacy.product.v2.domain.ProductV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductV3Repository {

    Optional<ProductV3> findById(Long productId);

    List<ProductV3> findProductsInStock();

    Page<ProductV3> findProductsInStockWithPage(Pageable pageable);

    void batchInsert(List<ProductV3> product, int batchSize);

    ProductV2 save(ProductV3 product);

    List<ProductV3> findAll();

    void deleteAll();

    Optional<ProductV3> findByIdWithOptimisticLock(Long productId);

    void updateProductStock(Long id, int stock, int deductedStock);
}
