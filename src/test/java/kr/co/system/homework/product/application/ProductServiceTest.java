package kr.co.system.homework.product.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.co.system.homework.legacy.product.v1.application.ProductV1Service;
import kr.co.system.homework.legacy.product.v1.domain.ProductV1;
import kr.co.system.homework.legacy.product.v1.domain.ProductV1Repository;
import kr.co.system.homework.support.exception.SoldOutException;
import kr.co.system.homework.product.domain.ProductFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static kr.co.system.homework.product.domain.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductV1Repository productRepository;
    @Autowired
    private ProductV1Service productService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @AfterEach
    public void init() {
        productRepository.deleteAll();
    }

    @DisplayName("상품의 재고 변경은 비관적락을 통해 진행된다.")
    @Test
    void findByIdWithLock2() {
        // given
        String quantityString = "100";
        ProductV1 savedProduct = productRepository.save(ProductFixture.product(quantityString));

        // when
        for (int idx = 1; idx <= 100; idx++) {
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
            ProductV1 productWithLock = productService.getProductWithLockBy(savedProduct.getId());
            productService.deduct(productWithLock, 1);
            // then
            transactionManager.commit(status);
            ProductV1 result = productService.getProductWithLockBy(productWithLock.getId());
            assertThat(result.getStock()).isEqualTo(Integer.parseInt(quantityString) - idx);
        }
    }

    @DisplayName("만약 재고가 부족하면 SoldOutException이 발생한다.")
    @Test
    void deduct() {
        // given
        String quantityString = "1";
        ProductV1 savedProduct = productRepository.save(ProductFixture.product(quantityString));

        // when
        ProductV1 productWithLock = productService.getProductWithLockBy(savedProduct.getId());
        int tooManyQuantity = Integer.MAX_VALUE;
        // then
        assertThatThrownBy(() -> productService.deduct(productWithLock, tooManyQuantity))
                .isExactlyInstanceOf(SoldOutException.class);
    }
}
