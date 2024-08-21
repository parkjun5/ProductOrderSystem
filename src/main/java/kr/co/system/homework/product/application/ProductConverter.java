package kr.co.system.homework.product.application;

import kr.co.system.homework.product.domain.Product;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductConverter {

    private static final String PRODUCT_ID = "상품번호";
    private static final String PRODUCT_NAME = "상품명";
    private static final String PRODUCT_PRICE = "판매가격";
    private static final String PRODUCT_STOCK = "재고수량";
    private static final String DATA_EXCEPTION = "데이터가 올바르지 않습니다. data : ";

    public Product convertFrom(CSVRecord eachRecord) {

        if (eachRecord.values().length != 4) {
            throw new IllegalArgumentException(DATA_EXCEPTION + eachRecord);
        }

        try {
            long id = Long.parseLong(eachRecord.get(PRODUCT_ID));
            BigDecimal price = new BigDecimal(eachRecord.get(PRODUCT_PRICE));
            int stock = Integer.parseInt(eachRecord.get(PRODUCT_STOCK));
            Product product = Product.builder(eachRecord.get(PRODUCT_NAME), price)
                    .id(id)
                    .stock(stock)
                    .build();
            product.changeStatusSelling();
            return product;
        } catch (IllegalStateException | IllegalArgumentException exception) {
            throw new IllegalArgumentException(DATA_EXCEPTION + eachRecord);
        }
    }

}
