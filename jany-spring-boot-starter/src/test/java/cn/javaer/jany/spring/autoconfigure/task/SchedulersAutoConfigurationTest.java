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