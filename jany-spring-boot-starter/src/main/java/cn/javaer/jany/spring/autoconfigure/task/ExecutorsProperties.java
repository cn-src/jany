package cn.javaer.jany.spring.autoconfigure.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cn-src
 */
@ConfigurationProperties(prefix = "jany.task")
public class ExecutorsProperties {

    @Getter
    @Setter
    private boolean enabled = false;

    @Getter
    private final Map<String, ExecutorConf> executors = new LinkedHashMap<>();
}