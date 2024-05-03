package kr.co._39cm.homework.product.v2.domain;

import kr.co._39cm.homework.support.domain.LongTypeIdentifier;
import kr.co._39cm.homework.support.jpa.hibernate.LongTypeIdentifierJavaType;
import org.hibernate.type.descriptor.WrapperOptions;

public class ProductId extends LongTypeIdentifier {
    public ProductId(Long id) {
        super(id);
    }

    public static ProductId of(Long id) {
        return new ProductId(id);
    }

    public static class ProductIdJavaType extends LongTypeIdentifierJavaType<ProductId> {
        public ProductIdJavaType() {
            super(ProductId.class);
        }

        @Override
        public <X> ProductId wrap(X value, WrapperOptions options) {
            if (value == null) return null;
            if (value instanceof ProductId productId) {
                return productId;
            }
            if (value instanceof Long longValue) {
                return ProductId.of(longValue);
            }
            throw unknownWrap(value.getClass());
        }
    }


}
