package kr.co._39cm.homework.product.ui;

import kr.co._39cm.homework.legacy.product.v1.domain.ProductV1;
import kr.co._39cm.homework.legacy.product.v1.domain.ProductInfoV1;

import java.util.List;

public class ProductPrinter {
    
    private ProductPrinter() {

    }

    public static void displayCurrentProducts(List<ProductV1> products) {
        printInputTitle();
        for (ProductV1 product : products) {
            printInputData(product);
        }
        System.out.println();
    }

    private static void printInputTitle() {
        System.out.println("상품번호\t\t상품명\t\t\t\t\t\t\t\t판매가격\t\t" + "재고수");
    }

    private static void printInputData(ProductV1 product) {
        ProductInfoV1 productInfo = product.getProductInfo();
        System.out.println(product.getId() + "\t\t" + productInfo.getProductName() + "\t\t" + product.getProductInfo().getPrice().intValue() + "\t\t" + product.getStock());
    }

}
