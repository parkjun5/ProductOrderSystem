package kr.co._29cm.homework.product.infra;


import jakarta.persistence.LockModeType;
import kr.co._29cm.homework.product.domain.BatchProductRepository;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<Product, Long>, ProductRepository, BatchProductRepository {

    Optional<Product> findById(Long productId);

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> findProductsInStock();
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdWithPessimisticLock(@Param("productId") Long productId);

    @Modifying
    @Query("UPDATE Product p SET p.stock = :deductedStock WHERE p.id = :productId AND p.stock = :oriStock")
    void updateProductStock(@Param("productId") Long productId,
                            @Param("oriStock")  int oriStock,
                            @Param("deductedStock") int deductedStock
    );
    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    Page<Product> findProductsInStockWithPage(Pageable pageable);
}
