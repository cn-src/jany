package cn.javaer.jany.spring.autoconfigure.web.exception;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.web.exception")
public class ExceptionMappingProperties {

    private Map<String, Error> mapping;

    @Data
    public static class Error {
        @NonNull
        private String error;
        @NonNull
        private Integer status;
    }
}