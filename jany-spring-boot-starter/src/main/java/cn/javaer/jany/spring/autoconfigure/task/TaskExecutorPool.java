package cn.javaer.jany.spring.autoconfigure.task;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author cn-src
 */
public class TaskExecutorPool implements BeanFactoryAware {
    private BeanFactory beanFactory;

    private final ExecutorsProperties executorsProperties;

    public TaskExecutorPool(ExecutorsProperties executorsProperties) {
        this.executorsProperties = executorsProperties;
    }

    public ThreadPoolTaskExecutor createByName(String name) {
        final ExecutorConf properties = executorsProperties.getExecutors().get(name);
        return createBuilder(properties).build();
    }

    public TaskExecutorBuilder createBuilder(ExecutorConf taskProp) {
        final ObjectProvider<TaskExecutorCustomizer> customizers =
            beanFactory.getBeanProvider(TaskExecutorCustomizer.class);
        final ObjectProvider<TaskDecorator> decorators =
            beanFactory.getBeanProvider(TaskDecorator.class);

        final ExecutorConf.Pool pool = taskProp.getPool();
        TaskExecutorBuilder builder = new TaskExecutorBuilder();
        builder = builder.queueCapacity(pool.getQueueCapacity());
        builder = builder.corePoolSize(pool.getCoreSize());
        builder = builder.maxPoolSize(pool.getMaxSize());
        builder = builder.allowCoreThreadTimeOut(pool.isAllowCoreThreadTimeout());
        builder = builder.keepAlive(pool.getKeepAlive());

        final ExecutorConf.Shutdown shutdown = taskProp.getShutdown();
        builder = builder.awaitTermination(shutdown.isAwaitTermination());
        builder = builder.awaitTerminationPeriod(shutdown.getAwaitTerminationPeriod());
        builder = builder.threadNamePrefix(taskProp.getThreadNamePrefix());
        builder = builder.customizers(customizers.orderedStream()::iterator);
        builder = builder.taskDecorator(decorators.getIfUnique());
        return builder;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}