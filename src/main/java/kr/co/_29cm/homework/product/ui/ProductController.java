package kr.co._29cm.homework.product.ui;

import kr.co._29cm.homework.ProductOrderProgramApplication;
import kr.co._29cm.homework.product.application.ProductReader;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.domain.Product;
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
