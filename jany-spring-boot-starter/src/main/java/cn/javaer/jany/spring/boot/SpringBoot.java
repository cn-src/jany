package cn.javaer.jany.spring.boot;

import cn.javaer.jany.JanyVersion;
import cn.javaer.jany.util.IoUtils;
import cn.javaer.jany.util.ReflectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author cn-src
 */
public class SpringBoot {

    public static ConfigurableApplicationContext run(final Class<?> primarySource,
                                                     final String... args) {
        final SpringApplication app = new SpringApplication(primarySource);

        final InputStream input = SpringBoot.class.getResourceAsStream("/default-boot.properties");
        final Properties props = IoUtils.readProperties(input);

        ReflectUtils.getClass("de.codecentric.boot.admin.server.config.EnableAdminServer")
            .ifPresent(aClass -> {
                final InputStream adminInput = SpringBoot.class
                    .getResourceAsStream("/default-boot-admin-local.properties");
                final Properties adminProps = IoUtils.readProperties(adminInput);
                props.putAll(adminProps);
            });
        ReflectUtils.getClass("com.yomahub.tlog.core.enhance.logback.AspectLogbackEncoder")
            .ifPresent(aClass ->
                props.put("logging.config", "classpath:cn/javaer/jany/spring/boot/logging/logback/tlog-logback.xml")
            );
        props.put("jany.version", JanyVersion.getVersion());
        props.put("spring.banner.location", "classpath:jany-banner.txt");
        app.setDefaultProperties(props);
        return app.run(args);
    }
}