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

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.boot.task.ThreadPoolTaskExecutorCustomizer;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author cn-src
 */
public class TaskExecutorFactory implements BeanFactoryAware {
    private BeanFactory beanFactory;

    private final ExecutorsProperties executorsProperties;

    public TaskExecutorFactory(ExecutorsProperties executorsProperties) {
        this.executorsProperties = executorsProperties;
    }

    public ThreadPoolTaskExecutor create(String name) {
        final ExecutorConf properties = executorsProperties.getExecutors().get(name);
        return createBuilder(properties).build();
    }

    private ThreadPoolTaskExecutorBuilder createBuilder(ExecutorConf taskProp) {
        final ObjectProvider<ThreadPoolTaskExecutorCustomizer> customizers =
                beanFactory.getBeanProvider(ThreadPoolTaskExecutorCustomizer.class);
        final ObjectProvider<TaskDecorator> decorators =
                beanFactory.getBeanProvider(TaskDecorator.class);

        final ExecutorConf.Pool pool = taskProp.getPool();
        ThreadPoolTaskExecutorBuilder builder = new ThreadPoolTaskExecutorBuilder();
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
    public void setBeanFactory(@NotNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}