package kr.co.system.homework.product.ui;

import kr.co.system.homework.product.domain.Product;
import kr.co.system.homework.product.domain.ProductInfo;

import java.util.List;

public class ProductPrinter {

    private ProductPrinter() {

    }

    public static void displayCurrentProducts(List<Product> products) {
        printInputTitle();
        for (Product product : products) {
            printInputData(product);
        }
        System.out.println();
    }

    private static void printInputTitle() {
        System.out.println("상품번호\t\t상품명\t\t\t\t\t\t\t\t판매가격\t\t" + "재고수");
    }

    private static void printInputData(Product product) {
        ProductInfo productInfo = product.getProductInfo();
        System.out.println(product.getId() + "\t\t" + productInfo.getProductName() + "\t\t" + product.getProductInfo().getPrice().intValue() + "\t\t" + product.getStock());
    }

}
