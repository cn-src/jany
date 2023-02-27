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

package cn.javaer.jany.spring.autoconfigure.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author cn-src
 */
@ConditionalOnClass(ThreadPoolTaskScheduler.class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SchedulersProperties.class)
@ConditionalOnProperty(prefix = "jany.scheduling", name = "enabled", havingValue = "true")
public class SchedulersAutoConfiguration {
    @Bean
    public TaskSchedulerFactory taskExecutorPool(SchedulersProperties properties) {
        return new TaskSchedulerFactory(properties);
    }
}