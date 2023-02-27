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
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.lang.reflect.InvocationTargetException;

/**
 * @author cn-src
 */
public class TaskSchedulerFactory implements BeanFactoryAware {
    private BeanFactory beanFactory;

    private final SchedulersProperties schedulersProperties;

    public TaskSchedulerFactory(SchedulersProperties schedulersProperties) {
        this.schedulersProperties = schedulersProperties;
    }

    public ThreadPoolTaskScheduler create(String name) {
        final SchedulerConf properties = schedulersProperties.getSchedulers().get(name);
        return createBuilder(properties).build();
    }

    private TaskSchedulerBuilder createBuilder(SchedulerConf properties) {
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
    public void setBeanFactory(@NotNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}