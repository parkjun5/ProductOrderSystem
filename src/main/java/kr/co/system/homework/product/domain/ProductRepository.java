package kr.co.system.homework.product.domain;

import kr.co.system.homework.legacy.product.v2.domain.ProductV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long productId);

    List<Product> findProductsInStock();

    Page<Product> findProductsInStockWithPage(Pageable pageable);

    void batchInsert(List<Product> product, int batchSize);

    ProductV2 save(Product product);

    List<Product> findAll();

    void deleteAll();

    Optional<Product> findByIdWithOptimisticLock(Long productId);

    void updateProductStock(Long id, int stock, int deductedStock);
}
