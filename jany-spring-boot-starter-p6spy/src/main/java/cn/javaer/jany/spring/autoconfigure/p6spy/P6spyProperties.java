package cn.javaer.jany.spring.autoconfigure.p6spy;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.p6spy")
public class P6spyProperties {

    private boolean enabled = false;
}