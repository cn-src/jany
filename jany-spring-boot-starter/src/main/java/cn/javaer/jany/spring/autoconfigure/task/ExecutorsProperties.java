package cn.javaer.jany.spring.autoconfigure.task;

import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cn-src
 */
@ConfigurationProperties(prefix = "jany.execution")
public class ExecutorsProperties {
    @NestedConfigurationProperty
    private Map<String, TaskProperties> executors = new LinkedHashMap<>();

    public Map<String, TaskProperties> getExecutions() {
        return this.executors;
    }

    public void setExecutors(final Map<String, TaskProperties> executions) {
        this.executors = executions;
    }

    static class TaskProperties extends TaskExecutionProperties {
    }
}