package kr.co.system.homework.product.ui.dto;

import kr.co.system.homework.product.domain.ProductStatus;

import java.math.BigDecimal;

public record ProductStockView(
        long id,
        String productName,
        BigDecimal price,
        ProductStatus productStatus,
        long version,
        long stock
) {
}
