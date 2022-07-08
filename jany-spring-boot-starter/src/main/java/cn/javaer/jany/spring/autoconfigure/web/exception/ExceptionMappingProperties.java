package cn.javaer.jany.spring.autoconfigure.web.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.web.exception")
public class ExceptionMappingProperties {

    @Getter
    @Setter
    private boolean enabled = true;

    private Map<String, String> mapping;
}