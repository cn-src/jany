package cn.javaer.jany.spring.autoconfigure;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * @author cn-src
 */
public class TestDataSource {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().url("jdbc:h2:mem:" + UUID.randomUUID()).username("sa").build();
    }
}