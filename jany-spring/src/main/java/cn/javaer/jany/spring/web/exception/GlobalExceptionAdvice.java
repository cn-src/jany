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

package cn.javaer.jany.spring.web.exception;

import cn.hutool.core.util.StrUtil;
import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.exception.RuntimeErrorInfo;
import cn.javaer.jany.spring.web.WebContext;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * @author cn-src
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ErrorProperties errorProperties;

    private final ErrorInfoProcessor errorInfoProcessor;

    public GlobalExceptionAdvice(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                 final ErrorProperties errorProperties,
                                 final ErrorInfoProcessor errorInfoProcessor) {
        this.errorProperties = errorProperties;
        this.errorInfoProcessor = errorInfoProcessor;
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseEntity<RuntimeErrorInfo> handleBadRequestException(
            final HttpServletRequest request, final Exception e) {
        final ErrorInfo errorInfo = errorInfoProcessor.getErrorInfo(e);
        final RuntimeErrorInfo runtimeErrorInfo = new RuntimeErrorInfo(errorInfo);
        String message = ErrorMessageSource.getMessage(errorInfo, e);
        if (StrUtil.isEmpty(message) && StrUtil.isNotEmpty(errorInfo.getMessage())) {
            message = errorInfo.getMessage();
        }
        runtimeErrorInfo.setMessage(message);
        this.fillInfo(runtimeErrorInfo, request, e);
        if (runtimeErrorInfo.getStatus() < 500) {
            this.logger.debug("Http status {}", runtimeErrorInfo.getStatus(), e);
        }
        else {
            this.logger.error("Http status {}", runtimeErrorInfo.getStatus(), e);
        }
        return ResponseEntity.status(runtimeErrorInfo.getStatus()).body(runtimeErrorInfo);
    }

    private void fillInfo(final RuntimeErrorInfo runtimeErrorInfo,
                          final HttpServletRequest request,
                          final Exception e) {
        runtimeErrorInfo.setPath(request.getServletPath());
        runtimeErrorInfo.setTimestamp(LocalDateTime.now());
        runtimeErrorInfo.setRequestId(WebContext.requestId());
        // exception
        final String clazz = e.getClass().getName();
        if (this.errorProperties.isIncludeException()) {
            runtimeErrorInfo.setException(clazz);
        }

        if (ErrorProperties.IncludeAttribute.ALWAYS.equals(this.errorProperties.getIncludeStacktrace())) {
            final String traceMessage = errorInfoProcessor.getTraceMessage(e);
            if (traceMessage != null) {
                runtimeErrorInfo.setTraceMessage(traceMessage);
            }
            else {
                runtimeErrorInfo.setTraceMessage(e.getMessage());
            }
            final StringWriter stackTrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stackTrace));
            stackTrace.flush();
            runtimeErrorInfo.setTrace(stackTrace.toString());
        }
    }
}