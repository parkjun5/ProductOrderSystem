package kr.co._39cm.homework.product.infra;

import kr.co._39cm.homework.legacy.product.v1.domain.ProductV1;
import kr.co._39cm.homework.legacy.product.v1.domain.ProductV1Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductV1Repository {
    private final Map<Long, ProductV1> products = new HashMap<>();

    @Override
    public Optional<ProductV1> findById(Long productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public List<ProductV1> findProductsInStock() {
        return products.values().stream().filter(it -> it.getStock() > 0).toList();
    }

    @Override
    public Page<ProductV1> findProductsInStockWithPage(Pageable pageable) {
        List<ProductV1> filteredProducts = products.values().stream()
                .filter(product -> product.getStock() > 0)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredProducts.size());
        List<ProductV1> pageContent = filteredProducts.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filteredProducts.size());
    }

    @Override
    public void batchInsert(List<ProductV1> products, int batchSize) {
        for (ProductV1 product : products) {
            this.products.put(product.getId(), product);
        }
    }

    @Override
    public ProductV1 save(ProductV1 product) {
        return products.put(product.getId(), product);
    }

    @Override
    public void deleteAll() {
        products.clear();
    }

    @Override
    public Optional<ProductV1> findByIdWithPessimisticLock(Long productId) {
        return Optional.of(products.get(productId));
    }

    @Override
    public void updateProductStock(Long id, int stock, int deductedStock) {
        if (products.containsKey(id)) {
            products.get(id).changeStock(deductedStock);
        }
    }

}
