package kr.co._29cm.homework.product.infra;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;

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