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

package cn.javaer.jany.ebean;

import cn.hutool.extra.spring.SpringUtil;
import cn.javaer.jany.util.ReflectUtils;
import io.ebean.config.AutoConfigure;
import io.ebean.config.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.StreamSupport;

/**
 * @author cn-src
 */
public class JanyAutoConfigure implements AutoConfigure {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void preConfigure(DatabaseConfig config) {
        config.add(JanyBeanPersistController.INSTANCE);
        ReflectUtils.getClass("org.springframework.core.env.Environment")
            .ifPresent(clazz -> supportSpring(config));
    }

    @Override
    public void postConfigure(DatabaseConfig config) {

    }

    @SuppressWarnings("rawtypes")
    private void supportSpring(DatabaseConfig config) {
        Properties properties = config.getProperties();
        final Environment env;
        try {
            env = SpringUtil.getBean(Environment.class);
        }
        catch (NullPointerException e) {
            log.warn("Can not get spring bean Environment", e);
            return;
        }
        final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
        StreamSupport.stream(sources.spliterator(), false)
            .filter(ps -> ps instanceof EnumerablePropertySource)
            .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
            .flatMap(Arrays::stream)
            .distinct()
            .filter(prop -> prop.startsWith("ebean.") || prop.startsWith("datasource."))
            .forEach(prop -> properties.put(prop, env.getProperty(prop)));
    }
}