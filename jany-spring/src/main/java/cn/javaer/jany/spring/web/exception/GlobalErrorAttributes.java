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

import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.exception.RuntimeErrorInfo;
import cn.javaer.jany.spring.web.WebContext;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author cn-src
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    private final ErrorInfoProcessor extractor;

    public GlobalErrorAttributes(ErrorInfoProcessor extractor) {
        this.extractor = extractor;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                  ErrorAttributeOptions options) {
        final Map<String, Object> attributes = super.getErrorAttributes(webRequest, options);
        attributes.put(RuntimeErrorInfo.Fields.traceMessage, attributes.get("message"));
        attributes.put(RuntimeErrorInfo.Fields.timestamp, LocalDateTime.now());
        attributes.put(RuntimeErrorInfo.Fields.requestId, WebContext.requestId());

        final Throwable t = super.getError(webRequest);
        if (t != null) {
            final RuntimeErrorInfo errorInfo = extractor.getRuntimeErrorInfo(t);
            attributes.put(RuntimeErrorInfo.Fields.message, errorInfo.getMessage());
            attributes.put(RuntimeErrorInfo.Fields.error, errorInfo.getError());
            attributes.put(RuntimeErrorInfo.Fields.status, errorInfo.getStatus());
        }
        else {
            final int status = (Integer) attributes.get("status");
            final HttpStatus httpStatus = HttpStatus.resolve(status);
            if (httpStatus == null) {
                attributes.put(RuntimeErrorInfo.Fields.message, "No message");
                attributes.put(RuntimeErrorInfo.Fields.error, "UNKNOWN");
            }
            else {
                attributes.put(RuntimeErrorInfo.Fields.message, httpStatus.getReasonPhrase());
                attributes.put(RuntimeErrorInfo.Fields.error, httpStatus.name());
            }
        }
        return attributes;
    }
}