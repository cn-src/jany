package cn.javaer.jany.spring.autoconfigure.springdoc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.springdoc")
public class SpringdocProperties {

    private boolean enabled = true;
}