package cn.javaer.jany.spring.autoconfigure.task;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author cn-src
 */
public class SchedulingProperties {

    @Getter
    @Setter
    private Class<? extends RejectedExecutionHandler> rejectedExecutionHandler;

    @Getter
    private final Pool pool = new Pool();

    @Getter
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