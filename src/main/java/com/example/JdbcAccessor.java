package com.example;

import org.h2.Driver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/5/13
 * Time: 10:12 AM
 */
public class JdbcAccessor {
    public static final String JDBC_URL = "jdbc:h2:mem:bob;DB_CLOSE_DELAY=-1";
    private DataSource dataSource = new SimpleDriverDataSource(new Driver(), JDBC_URL);
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    public void init() {
        jdbcTemplate.execute("create table if not exists numbers (the_number bigint)");
    }

    public void insert(long number) {
        artificialDelay();
        jdbcTemplate.update("insert into numbers values(?)", number);
    }

    public List<Long> getInsertedNumbers() {
        return jdbcTemplate.query("select the_number from numbers", new NumberMapper());
    }

    private void artificialDelay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Unexpected interrupt", e);
        }
    }

    private static class NumberMapper implements RowMapper<Long> {
        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong("the_number");
        }
    }
}
