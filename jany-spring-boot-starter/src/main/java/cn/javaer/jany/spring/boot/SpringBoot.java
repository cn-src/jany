package cn.javaer.jany.spring.boot;

import cn.javaer.jany.util.IoUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.InputStream;

/**
 * @author cn-src
 */
public class SpringBoot {
    public static ConfigurableApplicationContext run(final Class<?> primarySource,
                                                     final String... args) {
        final SpringApplication app = new SpringApplication(primarySource);
        final InputStream input = SpringBoot.class.getResourceAsStream("/default-boot.properties");
        app.setDefaultProperties(IoUtils.readProperties(input));
        return app.run(args);
    }
}