package cn.javaer.jany.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

/**
 * @author cn-src
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldNameConstants
public class RuntimeErrorInfo {
    @NotNull
    private final String error;
    private int status;

    private String message;
    private String path;
    private String requestId;
    private String exception;
    private String trace;
    private String traceMessage;
    private LocalDateTime timestamp;

    public RuntimeErrorInfo(final ErrorInfo errorInfo) {
        this.error = errorInfo.getError();
        this.status = errorInfo.getStatus();
    }
}