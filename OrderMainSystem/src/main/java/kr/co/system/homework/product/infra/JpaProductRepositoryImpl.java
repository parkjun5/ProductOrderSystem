package kr.co.system.homework.product.infra;

import kr.co.system.homework.product.domain.BatchProductRepository;
import kr.co.system.homework.product.domain.Product;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JpaProductRepositoryImpl implements BatchProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JpaProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void batchInsert(List<Product> products, int batchSize) {
        String sql = """
                INSERT INTO
                    product (product_id, product_name, price, stock, version, product_status)
                VALUES
                    (?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.batchUpdate(sql, new ProductInsertSetter(products, batchSize));
    }

    class ProductInsertSetter implements BatchPreparedStatementSetter {
        private final List<Product> products;
        private final int batchSize;

        ProductInsertSetter(List<Product> products, int batchSize) {
            this.products = products;
            this.batchSize = batchSize;
        }

        @Override
        public void setValues(PreparedStatement ps, int idx) throws SQLException {
            ps.setLong(1, products.get(idx).getId().longValue());
            ps.setString(2, products.get(idx).getProductInfo().getProductName());
            ps.setBigDecimal(3, products.get(idx).getProductInfo().getPrice());
            ps.setInt(4, products.get(idx).getStock());
            ps.setLong(5, products.get(idx).getVersion());
            ps.setString(6, products.get(idx).getProductInfo().getProductStatus().name());
        }

        @Override
        public int getBatchSize() {
            return Math.min(products.size(), batchSize);
        }
    }

}
