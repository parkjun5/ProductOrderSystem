package kr.co.system.homework.product.application;

import kr.co.system.homework.legacy.product.v2.domain.ProductV2;
import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.order.domain.CartItem;
import kr.co.system.homework.order.domain.OrderItemStatus;
import kr.co.system.homework.product.domain.Product;
import kr.co.system.homework.product.domain.ProductRepository;
import kr.co.system.homework.product.domain.ProductStatus;
import kr.co.system.homework.support.exception.SoldOutException;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductConverter productConverter;
    private final ProductRepository productRepository;

    public ProductService(ProductConverter productConverter, ProductRepository productRepository) {
        this.productConverter = productConverter;
        this.productRepository = productRepository;
    }

    public void batchInsert(List<CSVRecord> batch, int batchSize) {
        List<Product> products = batch.parallelStream()
                .map(productConverter::convertFrom)
                .toList();

        productRepository.batchInsert(products, batchSize);
    }

    public Page<Product> getOrderedAvailableProductsWithPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductsInStockWithPage(pageable);
    }

    public List<Product> getOrderedAvailableProducts() {
        return productRepository.findSellingProducts(ProductStatus.SELLING, OrderItemStatus.EXCLUDE_STATUSES)
                .stream()
                .map(it -> Product.builder(it.productName(), it.price())
                                  .id(it.id())
                                  .stock((int) it.stock()).build()
                )
                .sorted(Comparator.comparing(Product::getId).reversed())
                .toList();
    }

    public Product getProductByProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
    }

    public int getPreviousOrderedQuantityLessThanOrderTime(Long id, LocalDateTime orderTime) {
        return 0;
    }
}
