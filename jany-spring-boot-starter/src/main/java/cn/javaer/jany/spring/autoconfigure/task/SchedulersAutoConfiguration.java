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