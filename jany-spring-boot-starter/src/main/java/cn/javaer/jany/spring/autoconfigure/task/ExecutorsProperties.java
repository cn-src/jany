package cn.javaer.jany.spring.autoconfigure.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cn-src
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "jany.execution")
public class ExecutorsProperties {
    
    private Map<String, TaskProperties> executors = new LinkedHashMap<>();

    static class TaskProperties extends TaskExecutionProperties {
    }
}