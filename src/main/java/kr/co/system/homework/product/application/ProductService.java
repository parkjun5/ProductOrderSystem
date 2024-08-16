package kr.co.system.homework.product.application;

import kr.co.system.homework.order.domain.Cart;
import kr.co.system.homework.product.domain.Product;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    public void batchInsert(List<CSVRecord> batch, int batchSize) {

    }

    public Page<Product> getOrderedAvailableProductsWithPage(int page, int size) {
        return null;
    }

    public List<Product> getOrderedAvailableProducts() {
        return null;
    }

    public void validateStockInCart(Cart cart) {
    }

    public Product getProductWithLockBy(Long productId) {
        return null;
    }
}
