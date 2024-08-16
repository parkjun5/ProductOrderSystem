package kr.co.system.homework.product.ui;

import kr.co.system.homework.ProductOrderProgramApplication;
import kr.co.system.homework.legacy.product.v1.application.ProductV1Reader;
import kr.co.system.homework.legacy.product.v1.application.ProductV1Service;
import kr.co.system.homework.legacy.product.v1.domain.ProductV1;
import kr.co.system.homework.product.application.ProductReader;
import kr.co.system.homework.product.application.ProductService;
import kr.co.system.homework.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private final ProductReader productReader;

    public ProductController(ProductService productService, ProductReader productReader) {
        this.productService = productService;
        this.productReader = productReader;
    }

    @GetMapping
    public Page<Product> products(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return productService.getOrderedAvailableProductsWithPage(page, size);
    }

    @PostMapping
    public void addMoreInventory() {
        productReader.addNewProductDataFrom(ProductOrderProgramApplication.BIG_INPUT_DATA_LOCATION);
    }
}
