package kr.co.system.homework.legacy.product.v1.infra;

import kr.co.system.homework.legacy.product.v1.domain.BatchProductRepository;
import kr.co.system.homework.legacy.product.v1.domain.ProductV1;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JpaProductV1RepositoryImpl implements BatchProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JpaProductV1RepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void batchInsert(List<ProductV1> products, int batchSize) {
        String sql = """
                     INSERT INTO
                         product (product_id, product_name, price, stock)
                     VALUES
                         (?, ?, ?, ?)
                     """;
        jdbcTemplate.batchUpdate(sql, new ProductV1InsertSetter(products, batchSize));
    }


    class ProductV1InsertSetter implements BatchPreparedStatementSetter {
        private final List<ProductV1> products;
        private final int batchSize;

        ProductV1InsertSetter(List<ProductV1> products, int batchSize) {
            this.products = products;
            this.batchSize = batchSize;
        }

        @Override
        public void setValues(PreparedStatement ps, int idx) throws SQLException {
            ps.setLong(1, products.get(idx).getId());
            ps.setString(2, products.get(idx).getProductInfo().getProductName());
            ps.setBigDecimal(3, products.get(idx).getProductInfo().getPrice());
            ps.setInt(4, products.get(idx).getStock());
        }

        @Override
        public int getBatchSize() {
            return Math.min(products.size(), batchSize);
        }
    }

}
