package cn.javaer.jany.spring.autoconfigure.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cn-src
 */
@ConfigurationProperties(prefix = "jany.scheduling")
public class SchedulersProperties {

    @Getter
    @Setter
    private boolean enabled = false;

    @Getter
    private final Map<String, SchedulerConf> schedulers = new LinkedHashMap<>();
}