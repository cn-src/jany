package cn.javaer.jany.spring.autoconfigure.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cn-src
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "jany.scheduling")
public class SchedulersProperties {

    private Map<String, SchedulingProperties> schedulers = new LinkedHashMap<>();
}