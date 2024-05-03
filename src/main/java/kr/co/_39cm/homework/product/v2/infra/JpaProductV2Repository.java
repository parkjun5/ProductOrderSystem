package kr.co._39cm.homework.product.v2.infra;


import jakarta.persistence.LockModeType;
import kr.co._39cm.homework.product.v2.domain.BatchProductV2Repository;
import kr.co._39cm.homework.product.v2.domain.ProductId;
import kr.co._39cm.homework.product.v2.domain.ProductV2;
import kr.co._39cm.homework.product.v2.domain.ProductV2Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductV2Repository extends JpaRepository<ProductV2, ProductId>, ProductV2Repository, BatchProductV2Repository {

    @Query("SELECT p FROM ProductV2 p WHERE p.id = :productId")
    Optional<ProductV2> findById(@Param("productId") ProductId productId);

    @Override
    List<ProductV2> findAll();

    @Query("SELECT p FROM ProductV2 p WHERE p.stock > 0")
    List<ProductV2> findProductsInStock();
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT p FROM ProductV2 p WHERE p.id = :productId")
    Optional<ProductV2> findByIdWithOptimisticLock(@Param("productId") ProductId productId);

    @Modifying
    @Query("UPDATE ProductV2 p SET p.stock = :deductedStock WHERE p.id = :productId AND p.stock = :oriStock")
    void updateProductStock(@Param("productId") ProductId productId,
                            @Param("oriStock")  int oriStock,
                            @Param("deductedStock") int deductedStock
    );
    @Query("SELECT p FROM ProductV2 p WHERE p.stock > 0")
    Page<ProductV2> findProductsInStockWithPage(Pageable pageable);
}
