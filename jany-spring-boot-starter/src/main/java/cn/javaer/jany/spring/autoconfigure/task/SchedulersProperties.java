package cn.javaer.jany.spring.autoconfigure.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author cn-src
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "jany.scheduling")
public class SchedulersProperties {
    
    @NestedConfigurationProperty
    private Map<String, TaskProperties> schedulers = new LinkedHashMap<>();

    @Getter
    @Setter
    public static class TaskProperties extends TaskSchedulingProperties {
        private Class<? extends RejectedExecutionHandler> rejectedExecutionHandler;
    }
}