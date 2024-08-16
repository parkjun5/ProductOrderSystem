package kr.co._39cm.homework.legacy.product.v1.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductV1Repository {

    Optional<ProductV1> findById(Long productId);

    List<ProductV1> findProductsInStock();
    Page<ProductV1> findProductsInStockWithPage(Pageable pageable);
    void batchInsert(List<ProductV1> product, int batchSize);

    ProductV1 save(ProductV1 product);

    void deleteAll();

    Optional<ProductV1> findByIdWithPessimisticLock(Long productId);

    void updateProductStock(Long id, int stock, int deductedStock);
}
