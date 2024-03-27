package kr.co._29cm.homework.product.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.support.exception.SoldOutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static kr.co._29cm.homework.product.domain.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
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
        Product savedProduct = productRepository.save(product(quantityString));

        // when
        for (int idx = 1; idx <= 100; idx++) {
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
            Product productWithLock = productService.getProductWithLockBy(savedProduct.getId());
            productService.deduct(productWithLock, 1);
            // then
            transactionManager.commit(status);
            Product result = productService.getProductWithLockBy(productWithLock.getId());
            assertThat(result.getStock()).isEqualTo(Integer.parseInt(quantityString) - idx);
        }
    }

    @DisplayName("만약 재고가 부족하면 SoldOutException이 발생한다.")
    @Test
    void deduct() {
        // given
        String quantityString = "1";
        Product savedProduct = productRepository.save(product(quantityString));

        // when
        Product productWithLock = productService.getProductWithLockBy(savedProduct.getId());
        int tooManyQuantity = Integer.MAX_VALUE;
        // then
        assertThatThrownBy(() -> productService.deduct(productWithLock, tooManyQuantity))
                .isExactlyInstanceOf(SoldOutException.class);
    }
}