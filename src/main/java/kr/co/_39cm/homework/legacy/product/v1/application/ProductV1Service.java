package kr.co._39cm.homework.legacy.product.v1.application;

import kr.co._39cm.homework.order.domain.Cart;
import kr.co._39cm.homework.order.domain.CartItem;
import kr.co._39cm.homework.legacy.product.v1.domain.ProductV1;
import kr.co._39cm.homework.legacy.product.v1.domain.ProductV1Repository;
import kr.co._39cm.homework.support.exception.SoldOutException;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class ProductV1Service {

    private final ProductV1Converter productConverter;
    private final ProductV1Repository productRepository;

    public ProductV1Service(ProductV1Converter productConverter, ProductV1Repository productRepository) {
        this.productConverter = productConverter;
        this.productRepository = productRepository;
    }

    public void validateStockInCart(Cart cart) {
        for (CartItem cartItem : cart.getCartItems()) {
            ProductV1 product = productRepository.findById(cartItem.getProductId())
                                                .orElseThrow(() -> new NoSuchElementException("product가 존재하지 않습니다. productId =" + cartItem.getProductId()));
            boolean notEnoughStock = product.hasNotEnoughStock(cartItem.getQuantity());
            if (notEnoughStock) {
                throw new SoldOutException();
            }
        }
    }

    public List<ProductV1> getOrderedAvailableProducts() {
        return productRepository.findProductsInStock()
                .stream()
                .sorted(Comparator.comparing(ProductV1::getId).reversed())
                .toList();
    }

    public Page<ProductV1> getOrderedAvailableProductsWithPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductsInStockWithPage(pageable);
    }

    @Transactional
    public void batchInsert(List<CSVRecord> batch, int batchSize) {
        List<ProductV1> products = batch.parallelStream()
                                      .map(productConverter::convertFrom)
                                      .toList();
        productRepository.batchInsert(products, batchSize);
    }

    public ProductV1 getProductWithLockBy(Long productId) {
        return productRepository.findByIdWithPessimisticLock(productId)
                                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void deduct(ProductV1 product, int quantity) {
        int deductedStock = product.deductedStock(quantity);
        productRepository.updateProductStock(product.getId(), product.getStock(), deductedStock);
    }
}
