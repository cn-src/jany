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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;

/**
 * @author cn-src
 */
public class ExecutorConf {

    @Getter
    @NestedConfigurationProperty
    private final Pool pool = new Pool();

    @Getter
    @NestedConfigurationProperty
    private final Shutdown shutdown = new Shutdown();

    /**
     * Prefix to use for the names of newly created threads.
     */
    @Setter
    @Getter
    private String threadNamePrefix = "task-";

    @Setter
    @Getter
    public static class Pool {

        /**
         * 队列容量。无限容量不会增加池，因此会忽略 "max-size" 属性。
         */
        private int queueCapacity = Integer.MAX_VALUE;

        /**
         * 核心线程数。
         */
        private int coreSize = 8;

        /**
         * 允许的最大线程数。如果任务正在填满队列，则池可以扩展到该大小以适应负载。如果队列无界则忽略。
         */
        private int maxSize = Integer.MAX_VALUE;

        /**
         * Whether core threads are allowed to time out. This enables dynamic growing and
         * shrinking of the pool.
         */
        private boolean allowCoreThreadTimeout = true;

        /**
         * Time limit for which threads may remain idle before being terminated.
         */
        private Duration keepAlive = Duration.ofSeconds(60);
    }

    @Setter
    @Getter
    public static class Shutdown {

        /**
         * Whether the executor should wait for scheduled tasks to complete on shutdown.
         */
        private boolean awaitTermination;

        /**
         * Maximum time the executor should wait for remaining tasks to complete.
         */
        private Duration awaitTerminationPeriod;
    }
}