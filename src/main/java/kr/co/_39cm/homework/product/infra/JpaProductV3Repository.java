package kr.co._39cm.homework.product.infra;


import jakarta.persistence.LockModeType;
import kr.co._39cm.homework.product.domain.BatchProductV3Repository;
import kr.co._39cm.homework.product.domain.ProductV3;
import kr.co._39cm.homework.product.domain.ProductV3Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductV3Repository extends JpaRepository<ProductV3, Long>,
        ProductV3Repository,
        BatchProductV3Repository {

    @Query("SELECT p FROM ProductV3 p WHERE p.id = :productId")
    Optional<ProductV3> findById(@Param("productId") Long productId);

    @Override
    List<ProductV3> findAll();

    @Query("SELECT p FROM ProductV3 p WHERE p.stock > 0")
    List<ProductV3> findProductsInStock();

    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT p FROM ProductV3 p WHERE p.id = :productId")
    Optional<ProductV3> findByIdWithOptimisticLock(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE ProductV3 p SET p.stock = :deductedStock WHERE p.id = :productId AND p.stock = :oriStock")
    void updateProductStock(@Param("productId") Long productId,
                            @Param("oriStock") int oriStock,
                            @Param("deductedStock") int deductedStock
    );

    @Query("SELECT p FROM ProductV3 p WHERE p.stock > 0")
    Page<ProductV3> findProductsInStockWithPage(Pageable pageable);
}
