package kr.co.system.homework.legacy.product.v1.application;

import kr.co.system.homework.legacy.product.v1.domain.ProductV1;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductV1Converter {

    private static final String DATA_EXCEPTION = "데이터가 올바르지 않습니다. data : ";

    public ProductV1 convertFrom(CSVRecord eachRecord) {

        if (eachRecord.values().length != 4) {
            throw new IllegalArgumentException(DATA_EXCEPTION + eachRecord);
        }

        try {
            long id = Long.parseLong(eachRecord.get("상품번호"));
            String name = eachRecord.get("상품명");
            BigDecimal price = new BigDecimal(eachRecord.get("판매가격"));
            int stock = Integer.parseInt(eachRecord.get("재고수량"));

            return ProductV1.of(id, name, price, stock);

        } catch (IllegalStateException | IllegalArgumentException exception) {
            throw new IllegalArgumentException(DATA_EXCEPTION + eachRecord);
        }
    }

}
