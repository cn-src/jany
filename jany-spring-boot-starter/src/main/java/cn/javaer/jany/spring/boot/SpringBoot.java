package cn.javaer.jany.spring.boot;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.NetUtil;
import cn.javaer.jany.util.IoUtils;
import cn.javaer.jany.util.ReflectionUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
                final LinkedHashSet<InetAddress> localAddressList =
                    NetUtil.localAddressList(address ->
                        !address.isLoopbackAddress() && address instanceof Inet4Address);

                InetAddress used = null;

                if (CollUtil.isNotEmpty(localAddressList)) {
                    InetAddress address2 = null;
                    for (InetAddress inetAddress : localAddressList) {
                        try {
                            if (!inetAddress.isReachable(1)) {
                                continue;
                            }
                        }
                        catch (IOException ignore) {
                        }
                        if (!inetAddress.isSiteLocalAddress()) {
                            used = inetAddress;
                            break;
                        }
                        else if (null == address2) {
                            address2 = inetAddress;
                        }
                    }

                    if (null != address2) {
                        used = address2;
                    }
                }

                if (used == null) {
                    try {
                        used = InetAddress.getLocalHost();
                    }
                    catch (UnknownHostException e) {
                        // ignore
                    }
                }
                if (null != used) {
                    adminProps.put("spring.boot.admin.client.instance.service-url",
                        "http://${server.address:" + used.getHostAddress() + "}:${server" +
                            ".port:8080}");
                }
                props.putAll(adminProps);
            });
        app.setDefaultProperties(props);
        return app.run(args);
    }
}