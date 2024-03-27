package kr.co._29cm.homework;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class BigItemCreator {
    private static final String[] BRANDS = {"AeroTech", "BlueWave", "CyberLink", "Dreamscape", "EcoLife", "Fabrik", "GigaGear", "HyperTech"};
    private static final String[] PRODUCTS = {"Vacuum Tumbler", "Bluetooth Earphones", "Protein Chocolate", "Flip Flop", "Hand Sanitizer", "T-Shirt", "Bralette Set", "DJing Goods Set", "Wood Roll Table", "Daypack"};
    private static final String[] ATTRIBUTES = {"500ml", "Wireless", "Sugar-Free", "Large", "Mint", "Open Collar", "Special Edition", "Refurbished", "Anti-Lost Strap", "Ceramic"};
    private static final Random RANDOM = new Random();
    public void writeBigFiles() {

        String filePath = "src/main/resources/big_items.csv";

        try {
            Files.delete(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("파일이 존재하지 않습니다.");
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write("상품번호,상품명,판매가격,재고수량\n");
            for (int idx = 0; idx < 5_000; idx++) {
                fileWriter.write(generateProducts(10_000_000 + idx) + "\n");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String generateProducts(int productId) {
        String productName = generateProductName();
        int price = RANDOM.nextInt(100_000 - 100 + 1) + 100;
        int stock = RANDOM.nextInt(100 - 5 + 1) + 5;
        return  String.format("%d, %s, %d, %d", productId, productName, price, stock);
    }

    private String generateProductName() {
        String brand = BRANDS[RANDOM.nextInt(BRANDS.length)];
        String product = PRODUCTS[RANDOM.nextInt(PRODUCTS.length)];
        String attribute = ATTRIBUTES[RANDOM.nextInt(ATTRIBUTES.length)];
        return String.format("%s %s %s", brand, product, attribute);
    }

}
