package kr.co._29cm.homework.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long productId);

    List<Product> findProductsInStock();
    void batchInsert(List<Product> product, int batchSize);

    Product save(Product product);

    void deleteAll();

    Optional<Product> findByIdWithPessimisticLock(Long productId);

    void updateProductStock(Long id, int stock, int deductedStock);
}
