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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class ExecutorsAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(
            ExecutorsAutoConfiguration.class))
        .withPropertyValues(
            "jany.task.enabled=true",
            "jany.task.executors.demoExecutor1.threadNamePrefix=demo1-",
            "jany.task.executors.demoExecutor2.threadNamePrefix=demo2-"
        );

    @Test
    void postProcessBeanFactory() {

        this.contextRunner.withUserConfiguration(Conf.class).run(context -> {
            assertThat(context).hasBean("demoExecutor1");
            assertThat(context).hasBean("demoExecutor2");

            final ThreadPoolTaskExecutor demoExecutor1 = context.getBean("demoExecutor1",
                ThreadPoolTaskExecutor.class);
            assertThat(demoExecutor1.getThreadNamePrefix()).isEqualTo("demo1-");

            final ThreadPoolTaskExecutor demoExecutor2 = context.getBean("demoExecutor2",
                ThreadPoolTaskExecutor.class);
            assertThat(demoExecutor2.getThreadNamePrefix()).isEqualTo("demo2-");
        });
    }

    @Configuration
    static class Conf {
        @Bean
        public ThreadPoolTaskExecutor demoExecutor1(TaskExecutorFactory pool) {
            return pool.create("demoExecutor1");
        }

        @Bean
        public ThreadPoolTaskExecutor demoExecutor2(TaskExecutorFactory pool) {
            return pool.create("demoExecutor2");
        }
    }
}