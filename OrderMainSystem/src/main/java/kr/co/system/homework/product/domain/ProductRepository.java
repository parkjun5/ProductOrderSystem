package kr.co.system.homework.product.domain;

import kr.co.system.homework.order.domain.OrderItemStatus;
import kr.co.system.homework.product.ui.dto.ProductStockView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long productId);

    List<ProductStockView> findSellingProducts(ProductStatus productStatus, List<OrderItemStatus> excludedStatuses);

    Page<Product> findProductsInStockWithPage(Pageable pageable);

    void batchInsert(List<Product> product, int batchSize);

    Product save(Product product);

    List<Product> findAll();

    void deleteAll();

    Optional<Product> findByIdWithOptimisticLock(Long productId);

    void updateProductStock(Long id, int stock, int deductedStock);
}
