package kr.co.system.homework.legacy.product.v2.application;

import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.order.domain.CartItem;
import kr.co.system.homework.legacy.product.v2.domain.ProductV2;
import kr.co.system.homework.legacy.product.v2.domain.ProductV2Repository;
import kr.co.system.homework.support.exception.SoldOutException;
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
public class ProductV2Service {

    private final ProductV2Converter productConverter;
    private final ProductV2Repository productRepository;

    public ProductV2Service(ProductV2Converter productConverter, ProductV2Repository productRepository) {
        this.productConverter = productConverter;
        this.productRepository = productRepository;
    }

    public void validateStockInCart(Cart cart) {
        for (CartItem cartItem : cart.getCartItems()) {
            ProductV2 product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("product가 존재하지 않습니다. productId =" + cartItem.getProductId()));
            boolean notEnoughStock = product.hasNotEnoughStock(cartItem.getQuantity());
            if (notEnoughStock) {
                throw new SoldOutException();
            }
        }
    }

    public List<ProductV2> getOrderedAvailableProducts() {
        return productRepository.findProductsInStock()
                .stream()
                .sorted(Comparator.comparing((ProductV2 productV2) -> productV2.getId().longValue()).reversed())
                .toList();
    }

    public Page<ProductV2> getOrderedAvailableProductsWithPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductsInStockWithPage(pageable);
    }

    @Transactional
    public void batchInsert(List<CSVRecord> batch, int batchSize) {
        List<ProductV2> products = batch.parallelStream()
                .map(productConverter::convertFrom)
                .toList();
        productRepository.batchInsert(products, batchSize);
    }

    public ProductV2 getProductWithLockBy(Long productId) {
        return productRepository.findByIdWithOptimisticLock(productId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void deduct(ProductV2 product, int quantity) {
        int deductedStock = product.deductedStock(quantity);
        productRepository.updateProductStock(product.getId(), product.getStock(), deductedStock);
    }
}
