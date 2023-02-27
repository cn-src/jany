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
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author cn-src
 */
public class SchedulerConf {

    @Getter
    @Setter
    private Class<? extends RejectedExecutionHandler> rejectedExecutionHandler;

    @Getter
    @NestedConfigurationProperty
    private final Pool pool = new Pool();

    @Getter
    @NestedConfigurationProperty
    private final Shutdown shutdown = new Shutdown();

    /**
     * Prefix to use for the names of newly created threads.
     */
    @Getter
    @Setter
    private String threadNamePrefix = "scheduling-";

    @Getter
    @Setter
    public static class Pool {

        /**
         * Maximum allowed number of threads.
         */
        private int size = 1;
    }

    @Getter
    @Setter
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