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

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class SchedulersAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(
            SchedulersAutoConfiguration.class))
        .withPropertyValues(
            "jany.scheduling.enabled=true",
            "jany.scheduling.schedulers.demoScheduler1.threadNamePrefix=demo1-",
            "jany.scheduling.schedulers.demoScheduler2.threadNamePrefix=demo2-"
        );

    @Test
    void postProcessBeanFactory() {

        this.contextRunner.withUserConfiguration(Conf.class).run(context -> {
            assertThat(context).hasBean("demoScheduler1");
            assertThat(context).hasBean("demoScheduler2");

            final ThreadPoolTaskScheduler demoScheduler1 = context.getBean("demoScheduler1",
                ThreadPoolTaskScheduler.class);
            assertThat(demoScheduler1.getThreadNamePrefix()).isEqualTo("demo1-");

            final ThreadPoolTaskScheduler demoScheduler2 = context.getBean("demoScheduler2",
                ThreadPoolTaskScheduler.class);
            assertThat(demoScheduler2.getThreadNamePrefix()).isEqualTo("demo2-");
        });
    }

    @Configuration
    static class Conf {
        @Bean
        public ThreadPoolTaskScheduler demoScheduler1(TaskSchedulerFactory pool) {
            return pool.create("demoScheduler1");
        }

        @Bean
        public ThreadPoolTaskScheduler demoScheduler2(TaskSchedulerFactory pool) {
            return pool.create("demoScheduler2");
        }
    }
}