package cn.javaer.jany.spring.autoconfigure.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;

import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author cn-src
 */
@Getter
@Setter
public class SchedulingProperties extends TaskSchedulingProperties {
    private Class<? extends RejectedExecutionHandler> rejectedExecutionHandler;
}