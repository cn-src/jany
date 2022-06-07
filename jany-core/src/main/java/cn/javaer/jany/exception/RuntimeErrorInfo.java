package cn.javaer.jany.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "错误代码", required = true)
    private final String error;

    @Schema(description = "状态码", required = true)
    private int status;

    @Schema(description = "请求路径", required = true)
    private String path;

    @Schema(description = "时间戳", required = true)
    private LocalDateTime timestamp;

    @Schema(description = "提示消息")
    private String message;

    @Schema(description = "请求 Id")
    private String requestId;

    @Schema(description = "调试模式下，异常类型")
    private String exception;

    @Schema(description = "调试模式下，错误堆栈")
    private String trace;

    @Schema(description = "调试模式下，堆栈消息")
    private String traceMessage;

    public RuntimeErrorInfo(final ErrorInfo errorInfo) {
        this.error = errorInfo.getError();
        this.status = errorInfo.getStatus();
    }
}