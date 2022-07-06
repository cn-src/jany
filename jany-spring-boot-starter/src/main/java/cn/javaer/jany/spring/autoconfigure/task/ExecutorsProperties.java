package cn.javaer.jany.spring.autoconfigure.task;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cn-src
 */
@ConfigurationProperties(prefix = "jany.task")
public class ExecutorsProperties {

    @Getter
    private final Map<String, ExecutionProperties> executors = new LinkedHashMap<>();
}