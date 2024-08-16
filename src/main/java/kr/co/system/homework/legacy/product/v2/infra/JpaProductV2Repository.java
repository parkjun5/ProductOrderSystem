package kr.co.system.homework.legacy.product.v2.infra;


import jakarta.persistence.LockModeType;
import kr.co.system.homework.legacy.product.v2.domain.BatchProductV2Repository;
import kr.co.system.homework.legacy.product.v2.domain.ProductV2;
import kr.co.system.homework.legacy.product.v2.domain.ProductV2Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductV2Repository extends JpaRepository<ProductV2, Long>, ProductV2Repository, BatchProductV2Repository {

    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<ProductV2> findById(@Param("productId") Long productId);

    @Override
    List<ProductV2> findAll();

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<ProductV2> findProductsInStock();

    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<ProductV2> findByIdWithOptimisticLock(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Product p SET p.stock = :deductedStock WHERE p.id = :productId AND p.stock = :oriStock")
    void updateProductStock(@Param("productId") Long productId,
                            @Param("oriStock") int oriStock,
                            @Param("deductedStock") int deductedStock
    );

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    Page<ProductV2> findProductsInStockWithPage(Pageable pageable);
}
