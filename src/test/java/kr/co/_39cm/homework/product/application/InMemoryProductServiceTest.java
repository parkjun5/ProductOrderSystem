package kr.co._39cm.homework.product.application;

import kr.co._39cm.homework.BigItemCreator;
import kr.co._39cm.homework.order.common.domain.Cart;
import kr.co._39cm.homework.product.v1.application.ProductConverter;
import kr.co._39cm.homework.product.v1.application.ProductService;
import kr.co._39cm.homework.product.v1.domain.Product;
import kr.co._39cm.homework.product.infra.InMemoryProductRepository;
import kr.co._39cm.homework.support.exception.SoldOutException;
import kr.co._39cm.homework.product.domain.ProductFixture;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static kr.co._39cm.homework.order.domain.CartFixture.cart;
import static kr.co._39cm.homework.product.domain.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InMemoryProductServiceTest {

    private final InMemoryProductRepository productRepository = new InMemoryProductRepository();
    private final ProductService productService = new ProductService(new ProductConverter(), productRepository);

    @Test
    @DisplayName("재고가 충분한 지 확인한다.")
    void validateStockForOrder() {
        // given
        Product product = ProductFixture.product();
        productRepository.save(product);

        // when
        Cart cart = cart(product.getId().toString());

        // then
        assertDoesNotThrow(() -> productService.validateStockInCart(cart));
    }

    @Test
    @DisplayName("재고가 부족하면 SoldOutException이 발생한다.")
    void validateStockForOrder2() {
        // given
        Product product = ProductFixture.product();
        productRepository.save(product);

        // when
        String orderedProductId = product.getId().toString();
        Cart cart = cart(orderedProductId, "999999");

        // then
        assertThatThrownBy(() -> productService.validateStockInCart(cart))
                .isExactlyInstanceOf(SoldOutException.class)
                .hasMessage("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
    }

    @Test
    @DisplayName("재고가 1이상인 주문가능한 상품들만 가져온다.")
    void getAvailableProducts() {
        // given
        Product product1 = ProductFixture.product("1", "10");
        Product product2 = ProductFixture.product("2", "15");
        Product product3 = ProductFixture.product("3", "20");
        Product product4 = ProductFixture.product("4", "0");
        Product product5 = ProductFixture.product("5", "-1");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);

        // when
        List<Product> result = productService.getOrderedAvailableProducts();

        // then
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("배치 입력으로 데이터를 입력한다.")
    void batchInsert() {
        // given
        BigItemCreator bigItemCreator = new BigItemCreator();
        bigItemCreator.writeBigFiles();
        List<CSVRecord> csvRecords = batchReader();

        // when && then
        productService.batchInsert(csvRecords, 5_000);

        // then
        List<Product> firstResult = productService.getOrderedAvailableProducts();
        assertThat(firstResult).hasSize(5_000);
    }

    private List<CSVRecord> batchReader() {
        Path path = Paths.get("src/main/resources/big_items.csv");
        CSVFormat csvFormat = CSVFormat.Builder.create().setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();
        List<CSVRecord> batch = new ArrayList<>(5000);

        try (BufferedReader reader = Files.newBufferedReader(path);
             CSVParser parser = new CSVParser(reader, csvFormat)
        ) {
            for (CSVRecord record : parser) {
                batch.add(record);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return batch;
    }

}
