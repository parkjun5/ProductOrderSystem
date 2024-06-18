package kr.co._39cm.homework.product.v1.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long productId);

    List<Product> findProductsInStock();
    Page<Product> findProductsInStockWithPage(Pageable pageable);
    void batchInsert(List<Product> product, int batchSize);

    Product save(Product product);

    void deleteAll();

    Optional<Product> findByIdWithPessimisticLock(Long productId);

    void updateProductStock(Long id, int stock, int deductedStock);
}
