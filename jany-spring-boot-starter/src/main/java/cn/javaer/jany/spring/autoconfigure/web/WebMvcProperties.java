package cn.javaer.jany.spring.autoconfigure.web;

import lombok.Data;
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

    @Getter
    private final PageParam pageParam = new PageParam();

    @Data
    public static class PageParam {
        private int defaultMaxSize = 2000;
    }
}