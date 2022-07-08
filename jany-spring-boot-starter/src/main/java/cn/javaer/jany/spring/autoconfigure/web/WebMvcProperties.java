package cn.javaer.jany.spring.autoconfigure.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cn-src
 */
@ConfigurationProperties(prefix = "jany.web")
public class WebMvcProperties {
    @Getter
    @Setter
    private boolean enabled = true;
}