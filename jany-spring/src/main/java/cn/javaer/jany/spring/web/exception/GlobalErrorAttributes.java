/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.spring.exception.RuntimeErrorInfo;
import cn.javaer.jany.spring.web.WebAppContext;
import cn.javaer.jany.spring.exception.ErrorMessageSource;
import cn.javaer.jany.util.TimeUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Default implementation of {@link ErrorAttributes}. Provides the following attributes
 * when possible:
 * <ul>
 * <li>timestamp - The time that the errors were extracted</li>
 * <li>status - The status code</li>
 * <li>error - The error reason</li>
 * <li>exception - The class name of the root exception (if configured)</li>
 * <li>message - The exception message (if configured)</li>
 * <li>errors - Any {@link ObjectError}s from a {@link BindingResult} exception (if
 * configured)</li>
 * <li>trace - The exception stack trace (if configured)</li>
 * <li>path - The URL path when the exception was raised</li>
 * </ul>
 *
 * @author cn-src
 * @author Phillip Webb
 * @author Dave Syer
 * @author Stephane Nicoll
 * @author Vedran Pavic
 * @author Scott Frederick
 * @see ErrorAttributes
 * @since 2.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalErrorAttributes implements ErrorAttributes, HandlerExceptionResolver, Ordered {

    private static final String ERROR_ATTRIBUTE = GlobalErrorAttributes.class.getName() + ".ERROR";
    private final ErrorInfoExtractor errorInfoExtractor;

    /**
     * Create a new {@link GlobalErrorAttributes} instance.
     *
     * @param errorInfoExtractor errorInfoExtractor
     */
    public GlobalErrorAttributes(final ErrorInfoExtractor errorInfoExtractor) {
        this.errorInfoExtractor = errorInfoExtractor;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public ModelAndView resolveException(final @NotNull HttpServletRequest request,
                                         final @NotNull HttpServletResponse response,
                                         final Object handler,
                                         final @NotNull Exception ex) {
        this.storeErrorAttributes(request, ex);
        return null;
    }

    private void storeErrorAttributes(final HttpServletRequest request, final Exception ex) {
        request.setAttribute(ERROR_ATTRIBUTE, ex);
    }

    @Override
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest,
                                                  final ErrorAttributeOptions options) {
        final Map<String, Object> errorAttributes = this.getErrorAttributes(webRequest,
            options.isIncluded(Include.STACK_TRACE));

        if (!options.isIncluded(Include.EXCEPTION)) {
            errorAttributes.remove("exception");
        }
        if (!options.isIncluded(Include.STACK_TRACE)) {
            errorAttributes.remove("trace");
        }
        if (!options.isIncluded(Include.MESSAGE) && errorAttributes.get("message") != null) {
            errorAttributes.put("message", "");
        }
        if (!options.isIncluded(Include.BINDING_ERRORS)) {
            errorAttributes.remove("errors");
        }
        return errorAttributes;
    }

    // TODO
    @Deprecated
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest,
                                                  final boolean includeStackTrace) {
        final Throwable error = this.getError(webRequest);
        final RuntimeErrorInfo errorInfo = error == null ? null :
            this.errorInfoExtractor.getRuntimeErrorInfo(error, true);

        final Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", LocalDateTime.now().format(TimeUtils.DATE_TIME_FORMATTER));
        this.addStatus(errorAttributes, webRequest, errorInfo);
        this.addErrorDetails(errorAttributes, webRequest, includeStackTrace, errorInfo);
        this.addPath(errorAttributes, webRequest);
        errorAttributes.put(WebAppContext.REQUEST_ID_PARAM, WebAppContext.getRequestId());
        return errorAttributes;
    }

    private void addStatus(final Map<String, Object> errorAttributes,
                           final RequestAttributes requestAttributes,
                           final RuntimeErrorInfo errorInfo) {
        if (errorInfo != null) {
            errorAttributes.put("status", errorInfo.getStatus());
            errorAttributes.put("error", errorInfo.getError());
            return;
        }
        final Integer status = this.getAttribute(requestAttributes,
            RequestDispatcher.ERROR_STATUS_CODE);
        if (status == null) {
            errorAttributes.put("status", 999);
            errorAttributes.put("error", "None");
            return;
        }
        errorAttributes.put("status", status);
        try {
            errorAttributes.put("error", HttpStatus.valueOf(status).name());
        }
        catch (final Exception ex) {
            // Unable to obtain a reason
            errorAttributes.put("error", "Http Status " + status);
        }
    }

    private void addErrorDetails(final Map<String, Object> errorAttributes,
                                 final WebRequest webRequest,
                                 final boolean includeStackTrace,
                                 final RuntimeErrorInfo errorInfo) {
        Throwable error = this.getError(webRequest);
        if (error != null) {
            while (error instanceof ServletException && error.getCause() != null) {
                error = error.getCause();
            }
            errorAttributes.put("exception", error.getClass().getName());
            if (includeStackTrace) {
                this.addStackTrace(errorAttributes, error);
            }
        }
        if (null != errorInfo) {
            errorAttributes.put("message", errorInfo.getMessage());
            return;
        }

        final String message = ErrorMessageSource.getMessage((String) errorAttributes.get("error"),
            "No message available");
        errorAttributes.put("message", message);
    }

    private void addStackTrace(final Map<String, Object> errorAttributes, final Throwable error) {
        final StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        errorAttributes.put("trace", stackTrace.toString());
    }

    private void addPath(final Map<String, Object> errorAttributes,
                         final RequestAttributes requestAttributes) {
        final String path = this.getAttribute(requestAttributes,
            RequestDispatcher.ERROR_REQUEST_URI);
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }

    @Override
    public Throwable getError(final WebRequest webRequest) {
        final Throwable exception = this.getAttribute(webRequest, ERROR_ATTRIBUTE);
        return (exception != null) ? exception : this.getAttribute(webRequest,
            RequestDispatcher.ERROR_EXCEPTION);
    }

    @SuppressWarnings("unchecked")
    private <T> T getAttribute(final RequestAttributes requestAttributes, final String name) {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}