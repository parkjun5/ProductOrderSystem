package kr.co._29cm.homework.product.infra;

import kr.co._29cm.homework.product.domain.BatchProductRepository;
import kr.co._29cm.homework.product.domain.Product;
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
        jdbcTemplate.batchUpdate("INSERT INTO product (product_id, product_name, price, stock) VALUES (?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
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
        );
    }
}