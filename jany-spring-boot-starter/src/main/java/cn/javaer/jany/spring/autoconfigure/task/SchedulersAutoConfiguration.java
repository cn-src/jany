package cn.javaer.jany.spring.autoconfigure.task;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author cn-src
 */
@ConditionalOnClass(ThreadPoolTaskScheduler.class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SchedulersProperties.class)
@ConditionalOnProperty(prefix = "jany.scheduling", name = "enabled", havingValue = "true")
public class SchedulersAutoConfiguration implements ApplicationContextAware {

    private DefaultListableBeanFactory beanFactory;

    @PostConstruct
    public void init() {
        final SchedulersProperties schedulersProperties =
            beanFactory.getBean(SchedulersProperties.class);
        final ObjectProvider<TaskSchedulerCustomizer> taskSchedulerCustomizers =
            beanFactory.getBeanProvider(TaskSchedulerCustomizer.class);

        if (!CollectionUtils.isEmpty(schedulersProperties.getSchedulers())) {
            for (final Map.Entry<String, SchedulingProperties> entry :
                schedulersProperties.getSchedulers().entrySet()) {
                final SchedulingProperties properties = entry.getValue();

                TaskSchedulerBuilder builder = new TaskSchedulerBuilder();
                builder = builder.poolSize(properties.getPool().getSize());
                final SchedulingProperties.Shutdown shutdown = properties.getShutdown();
                builder = builder.awaitTermination(shutdown.isAwaitTermination());
                builder = builder.awaitTerminationPeriod(shutdown.getAwaitTerminationPeriod());
                builder = builder.threadNamePrefix(properties.getThreadNamePrefix());
                builder = builder.customizers(taskSchedulerCustomizers);

                final GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(ThreadPoolTaskExecutor.class);
                beanDefinition.setLazyInit(true);
                final TaskSchedulerBuilder fb = builder;
                beanDefinition.setInstanceSupplier(() -> {
                    final ThreadPoolTaskScheduler scheduler = fb.build();
                    if (properties.getRejectedExecutionHandler() != null) {
                        try {
                            scheduler.setRejectedExecutionHandler(properties.getRejectedExecutionHandler().getDeclaredConstructor().newInstance());
                        }
                        catch (final InstantiationException | IllegalAccessException |
                                     NoSuchMethodException | InvocationTargetException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    return scheduler;
                });
                ((DefaultListableBeanFactory) beanFactory)
                    .registerBeanDefinition(entry.getKey(), beanDefinition);
            }
        }
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        beanFactory = (DefaultListableBeanFactory)
            applicationContext.getAutowireCapableBeanFactory();
    }
}