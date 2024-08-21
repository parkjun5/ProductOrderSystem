package kr.co.system.homework.product.infra;


import jakarta.persistence.LockModeType;
import kr.co.system.homework.order.domain.OrderItemStatus;
import kr.co.system.homework.product.domain.BatchProductRepository;
import kr.co.system.homework.product.domain.Product;
import kr.co.system.homework.product.domain.ProductRepository;
import kr.co.system.homework.product.domain.ProductStatus;
import kr.co.system.homework.product.ui.dto.ProductStockView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<Product, Long>,
        ProductRepository,
        BatchProductRepository
{

    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findById(@Param("productId") Long productId);

    @Override
    List<Product> findAll();

    @Query(
        """
        SELECT
             new kr.co.system.homework.product.ui.dto.ProductStockView(
                p.id,
                p.productInfo.productName,
                p.productInfo.price,
                p.productInfo.productStatus,
                p.version,
                (p.stock - COALESCE(oi.orderedQuantity, 0))
             )
         FROM
             Product p
         LEFT JOIN
             (
                 SELECT
                     oi.product.id AS productId,
                     SUM(oi.selectedQuantity) AS orderedQuantity
                 FROM
                     OrderItem oi
                 WHERE
                     oi.orderItemStatus NOT IN (:excludedStatuses)
                 GROUP BY
                     oi.product.id
             ) oi ON p.id = oi.productId
         WHERE
             p.productInfo.productStatus = :productStatus
        """)
    List<ProductStockView> findSellingProducts(@Param("productStatus") ProductStatus productStatus,
                                               @Param("excludedStatuses") List<OrderItemStatus> excludedStatuses);

    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdWithOptimisticLock(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Product p SET p.stock = :deductedStock WHERE p.id = :productId AND p.stock = :oriStock")
    void updateProductStock(@Param("productId") Long productId,
                            @Param("oriStock") int oriStock,
                            @Param("deductedStock") int deductedStock
    );

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    Page<Product> findProductsInStockWithPage(Pageable pageable);
}
