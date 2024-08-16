package kr.co.system.homework.legacy.product.v1.infra;


import jakarta.persistence.LockModeType;
import kr.co.system.homework.legacy.product.v1.domain.BatchProductRepository;
import kr.co.system.homework.legacy.product.v1.domain.ProductV1;
import kr.co.system.homework.legacy.product.v1.domain.ProductV1Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductV1Repository extends JpaRepository<ProductV1, Long>, ProductV1Repository, BatchProductRepository {

    Optional<ProductV1> findById(Long productId);

    @Query("SELECT p FROM ProductV1 p WHERE p.stock > 0")
    List<ProductV1> findProductsInStock();
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    @Query("SELECT p FROM ProductV1 p WHERE p.id = :productId")
    Optional<ProductV1> findByIdWithPessimisticLock(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE ProductV1 p SET p.stock = :deductedStock WHERE p.id = :productId AND p.stock = :oriStock")
    void updateProductStock(@Param("productId") Long productId,
                            @Param("oriStock")  int oriStock,
                            @Param("deductedStock") int deductedStock
    );
    @Query("SELECT p FROM ProductV1 p WHERE p.stock > 0")
    Page<ProductV1> findProductsInStockWithPage(Pageable pageable);
}
