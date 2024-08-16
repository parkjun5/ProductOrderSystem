package kr.co._39cm.homework.product.ui;

import kr.co._39cm.homework.ProductOrderProgramApplication;
import kr.co._39cm.homework.legacy.product.v1.application.ProductV1Reader;
import kr.co._39cm.homework.legacy.product.v1.application.ProductV1Service;
import kr.co._39cm.homework.legacy.product.v1.domain.ProductV1;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductV1Service productService;
    private final ProductV1Reader productReader;

    public ProductController(ProductV1Service productService, ProductV1Reader productReader) {
        this.productService = productService;
        this.productReader = productReader;
    }

    @GetMapping
    public Page<ProductV1> products(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return productService.getOrderedAvailableProductsWithPage(page, size);
    }

    @PostMapping
    public void addMoreInventory() {
        productReader.addNewProductDataFrom(ProductOrderProgramApplication.BIG_INPUT_DATA_LOCATION);
    }
}
