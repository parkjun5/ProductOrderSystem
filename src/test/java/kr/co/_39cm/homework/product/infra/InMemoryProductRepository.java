package kr.co._39cm.homework.product.infra;

import kr.co._39cm.homework.product.v1.domain.Product;
import kr.co._39cm.homework.product.v1.domain.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public List<Product> findProductsInStock() {
        return products.values().stream().filter(it -> it.getStock() > 0).toList();
    }

    @Override
    public Page<Product> findProductsInStockWithPage(Pageable pageable) {
        List<Product> filteredProducts = products.values().stream()
                .filter(product -> product.getStock() > 0)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredProducts.size());
        List<Product> pageContent = filteredProducts.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filteredProducts.size());
    }

    @Override
    public void batchInsert(List<Product> products, int batchSize) {
        for (Product product : products) {
            this.products.put(product.getId(), product);
        }
    }

    @Override
    public Product save(Product product) {
        return products.put(product.getId(), product);
    }

    @Override
    public void deleteAll() {
        products.clear();
    }

    @Override
    public Optional<Product> findByIdWithPessimisticLock(Long productId) {
        return Optional.of(products.get(productId));
    }

    @Override
    public void updateProductStock(Long id, int stock, int deductedStock) {
        if (products.containsKey(id)) {
            products.get(id).changeStock(deductedStock);
        }
    }

}
