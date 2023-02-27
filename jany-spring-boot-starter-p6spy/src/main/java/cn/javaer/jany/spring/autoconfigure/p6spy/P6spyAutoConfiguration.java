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

package cn.javaer.jany.spring.autoconfigure.p6spy;

import cn.javaer.jany.p6spy.BeautifulFormat;
import cn.javaer.jany.p6spy.P6spyHelper;
import cn.javaer.jany.p6spy.TimestampJdbcEventListener;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.p6spy.engine.common.Loggable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc 支持.
 *
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Loggable.class, BeautifulFormat.class, TimestampJdbcEventListener.class})
@AutoConfigureBefore({DataSourceDecoratorAutoConfiguration.class})
@ConditionalOnProperty(prefix = "jany.p6spy", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(P6spyProperties.class)
public class P6spyAutoConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        P6spyHelper.initConfig();
    }
}