package cn.javaer.jany.spring.autoconfigure.task;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.lang.reflect.InvocationTargetException;

/**
 * @author cn-src
 */
public class SchedulerPool implements BeanFactoryAware {
    private BeanFactory beanFactory;

    private final SchedulersProperties schedulersProperties;

    public SchedulerPool(SchedulersProperties schedulersProperties) {
        this.schedulersProperties = schedulersProperties;
    }

    public ThreadPoolTaskScheduler createByName(String name) {
        final SchedulerConf properties = schedulersProperties.getSchedulers().get(name);
        return createBuilder(properties).build();
    }

    public TaskSchedulerBuilder createBuilder(SchedulerConf properties) {
        final ObjectProvider<TaskSchedulerCustomizer> taskSchedulerCustomizers =
            beanFactory.getBeanProvider(TaskSchedulerCustomizer.class);
        TaskSchedulerBuilder builder = new TaskSchedulerBuilder();
        builder = builder.poolSize(properties.getPool().getSize());
        final SchedulerConf.Shutdown shutdown = properties.getShutdown();
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
        return builder;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}