package cn.javaer.jany.spring.boot;

import cn.hutool.core.net.NetUtil;
import cn.javaer.jany.util.IoUtils;
import cn.javaer.jany.util.ReflectionUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.LinkedHashSet;
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

        ReflectionUtils.getClass("de.codecentric.boot.admin.server.config.EnableAdminServer")
            .ifPresent(aClass -> {
                final InputStream adminInput = SpringBoot.class
                    .getResourceAsStream("/default-boot-admin-local.properties");
                final Properties adminProps = IoUtils.readProperties(adminInput);
                final LinkedHashSet<InetAddress> addresses = NetUtil.localAddressList(address ->
                    !address.isLoopbackAddress() && address instanceof Inet4Address);

                InetAddress used = null;
                try {
                    for (InetAddress address : addresses) {
                        if (address.isSiteLocalAddress() && address.isReachable(1)) {
                            used = address;
                        }
                    }
                }
                catch (IOException ignore) {
                }
                if (null != used) {
                    adminProps.put("server.address", used.getHostAddress());
                }
                props.putAll(adminProps);
            });
        app.setDefaultProperties(props);
        return app.run(args);
    }
}