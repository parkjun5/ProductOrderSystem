package kr.co.system.homework.product.application;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductReader {

    private static final int BATCH_SIZE = 100;
    private static final String READ_ERROR_MSG = "input 파일을 읽던 중 에러 발생 input : {}";

    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ProductReader(ProductService productService) {
        this.productService = productService;
    }

    public void addNewProductDataFrom(String inputDataLocation) {
        Path path = Paths.get(inputDataLocation);
        CSVFormat csvFormat = CSVFormat.Builder.create().setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        try (BufferedReader reader = Files.newBufferedReader(path);
             CSVParser parser = new CSVParser(reader, csvFormat)
        ) {
            List<CSVRecord> batch = new ArrayList<>(BATCH_SIZE);

            for (CSVRecord eachRecord : parser) {
                batch.add(eachRecord);
                if (batch.size() == BATCH_SIZE) {
                    productService.batchInsert(batch, BATCH_SIZE);
                    batch.clear();
                }
            }

            if (!batch.isEmpty()) {
                productService.batchInsert(batch, BATCH_SIZE);
            }

        } catch (IOException e) {
            logger.error(READ_ERROR_MSG, inputDataLocation);
            throw new IllegalStateException(e);
        }
    }

}
