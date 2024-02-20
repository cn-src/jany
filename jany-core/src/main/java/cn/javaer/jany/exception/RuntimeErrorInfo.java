/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

/**
 * 运行时错误信息。
 *
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
        this.message = errorInfo.getMessage();
    }
}