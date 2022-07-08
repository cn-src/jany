package cn.javaer.jany.spring.autoconfigure.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author cn-src
 */
@ConditionalOnClass(ThreadPoolTaskExecutor.class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExecutorsProperties.class)
@ConditionalOnProperty(prefix = "jany.task", name = "enabled", havingValue = "true")
public class ExecutorsAutoConfiguration {
    @Bean
    public TaskExecutorPool taskExecutorPool(ExecutorsProperties executorsProperties) {
        return new TaskExecutorPool(executorsProperties);
    }
}