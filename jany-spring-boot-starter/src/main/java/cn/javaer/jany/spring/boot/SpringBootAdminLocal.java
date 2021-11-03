package cn.javaer.jany.spring.boot;

import cn.javaer.jany.util.IoUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author cn-src
 */
public class SpringBootAdminLocal {
    public static ConfigurableApplicationContext run(final Class<?> primarySource,
                                                     final String... args) {
        final SpringApplication app = new SpringApplication(primarySource);

        final InputStream input = SpringBoot.class.getResourceAsStream("/default-boot.properties");
        final Properties props = IoUtils.readProperties(input);

        final InputStream adminInput = SpringBoot.class
            .getResourceAsStream("/default-boot-admin-local.properties");
        final Properties adminProps = IoUtils.readProperties(adminInput);
        props.putAll(adminProps);

        app.setDefaultProperties(props);
        return app.run(args);
    }
}