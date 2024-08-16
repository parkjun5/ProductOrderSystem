package kr.co._39cm.homework.product.infra;

import kr.co._39cm.homework.product.domain.BatchProductV3Repository;
import kr.co._39cm.homework.product.domain.ProductV3;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JpaProductV2RepositoryImpl implements BatchProductV3Repository {

    private final JdbcTemplate jdbcTemplate;

    public JpaProductV2RepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void batchInsert(List<ProductV3> products, int batchSize) {
        String sql = """
                     INSERT INTO
                         product_v2 (product_id, product_name, price, stock, version)
                     VALUES
                         (?, ?, ?, ?, ?)
                     """;
        jdbcTemplate.batchUpdate(sql, new ProductV2InsertSetter(products, batchSize));
    }

}

class ProductV2InsertSetter implements BatchPreparedStatementSetter {
    private final List<ProductV3> products;
    private final int batchSize;

    ProductV2InsertSetter(List<ProductV3> products, int batchSize) {
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
    }

    @Override
    public int getBatchSize() {
        return Math.min(products.size(), batchSize);
    }
}
