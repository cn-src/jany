/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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