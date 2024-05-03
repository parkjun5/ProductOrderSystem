package kr.co._39cm.homework.product.application;

import kr.co._39cm.homework.order.domain.Cart;
import kr.co._39cm.homework.order.domain.CartItem;
import kr.co._39cm.homework.product.domain.Product;
import kr.co._39cm.homework.product.domain.ProductRepository;
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
public class ProductService {

    private final ProductConverter productConverter;
    private final ProductRepository productRepository;

    public ProductService(ProductConverter productConverter, ProductRepository productRepository) {
        this.productConverter = productConverter;
        this.productRepository = productRepository;
    }

    public void validateStockInCart(Cart cart) {
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = productRepository.findById(cartItem.getProductId())
                                                .orElseThrow(() -> new NoSuchElementException("product가 존재하지 않습니다. productId =" + cartItem.getProductId()));
            boolean notEnoughStock = product.hasNotEnoughStock(cartItem.getQuantity());
            if (notEnoughStock) {
                throw new SoldOutException();
            }
        }
    }

    public List<Product> getOrderedAvailableProducts() {
        return productRepository.findProductsInStock()
                .stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .toList();
    }

    public Page<Product> getOrderedAvailableProductsWithPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductsInStockWithPage(pageable);
    }

    @Transactional
    public void batchInsert(List<CSVRecord> batch, int batchSize) {
        List<Product> products = batch.parallelStream()
                                      .map(productConverter::convertFrom)
                                      .toList();
        productRepository.batchInsert(products, batchSize);
    }

    public Product getProductWithLockBy(Long productId) {
        return productRepository.findByIdWithPessimisticLock(productId)
                                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void deduct(Product product, int quantity) {
        int deductedStock = product.deductedStock(quantity);
        productRepository.updateProductStock(product.getId(), product.getStock(), deductedStock);
    }
}
