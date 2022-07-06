package cn.javaer.jany.spring.autoconfigure.task;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

/**
 * @author cn-src
 */
public class ExecutionProperties {

    @Getter
    private final Pool pool = new Pool();

    @Getter
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