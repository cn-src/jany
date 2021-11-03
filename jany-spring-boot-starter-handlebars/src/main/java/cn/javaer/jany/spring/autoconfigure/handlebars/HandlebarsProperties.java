package cn.javaer.jany.spring.autoconfigure.handlebars;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.handlebars")
public class HandlebarsProperties {

    public static final String DEFAULT_PREFIX = "file:"
        + System.getProperty("user.dir") + "/conf/templates/";

    public static final String DEFAULT_SUFFIX = ".hbs";

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private String prefix = DEFAULT_PREFIX;

    private String suffix = DEFAULT_SUFFIX;

    private Charset charset = DEFAULT_CHARSET;
}