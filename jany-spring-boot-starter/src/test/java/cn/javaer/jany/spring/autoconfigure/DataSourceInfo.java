package cn.javaer.jany.spring.autoconfigure;

import lombok.Builder;
import lombok.Value;

/**
 * @author cn-src
 */
@Builder
@Value
public class DataSourceInfo {
    String jdbcUrl;
    String username;
    String password;
}