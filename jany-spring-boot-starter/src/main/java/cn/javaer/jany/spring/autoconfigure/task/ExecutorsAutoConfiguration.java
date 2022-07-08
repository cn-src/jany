package cn.javaer.jany.spring.autoconfigure.task;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author cn-src
 */
@ConditionalOnClass(ThreadPoolTaskExecutor.class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExecutorsProperties.class)
@ConditionalOnProperty(prefix = "jany.task", name = "enabled", havingValue = "true")
public class ExecutorsAutoConfiguration implements ApplicationContextAware {

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        final ExecutorsProperties properties =
            applicationContext.getBean(ExecutorsProperties.class);
        final ObjectProvider<TaskExecutorCustomizer> customizers =
            applicationContext.getBeanProvider(TaskExecutorCustomizer.class);
        final ObjectProvider<TaskDecorator> decorators =
            applicationContext.getBeanProvider(TaskDecorator.class);
        final DefaultListableBeanFactory beanFactory =
            (DefaultListableBeanFactory) applicationContext
                .getAutowireCapableBeanFactory();

        if (!CollectionUtils.isEmpty(properties.getExecutors())) {
            for (final Map.Entry<String, ExecutionProperties> entry :
                properties.getExecutors().entrySet()) {
                final ExecutionProperties taskProp =
                    entry.getValue();
                final ExecutionProperties.Pool pool = taskProp.getPool();
                TaskExecutorBuilder builder = new TaskExecutorBuilder();
                builder = builder.queueCapacity(pool.getQueueCapacity());
                builder = builder.corePoolSize(pool.getCoreSize());
                builder = builder.maxPoolSize(pool.getMaxSize());
                builder = builder.allowCoreThreadTimeOut(pool.isAllowCoreThreadTimeout());
                builder = builder.keepAlive(pool.getKeepAlive());

                final ExecutionProperties.Shutdown shutdown = taskProp.getShutdown();
                builder = builder.awaitTermination(shutdown.isAwaitTermination());
                builder = builder.awaitTerminationPeriod(shutdown.getAwaitTerminationPeriod());
                builder = builder.threadNamePrefix(taskProp.getThreadNamePrefix());
                builder = builder.customizers(customizers.orderedStream()::iterator);
                builder = builder.taskDecorator(decorators.getIfUnique());

                final GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(ThreadPoolTaskExecutor.class);
                beanDefinition.setLazyInit(true);
                final TaskExecutorBuilder fb = builder;
                beanDefinition.setInstanceSupplier(fb::build);
                beanFactory.registerBeanDefinition(entry.getKey(), beanDefinition);
            }
        }
    }
}