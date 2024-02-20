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

import cn.javaer.jany.exception.ErrorCode;
import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.exception.RuntimeErrorInfo;
import cn.javaer.jany.util.IoUtils;
import cn.javaer.jany.util.ReflectUtils;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.dromara.hutool.core.text.StrUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

/**
 * @author cn-src
 */
public class ErrorInfoProcessorImpl implements ErrorInfoProcessor {

    private static final String SA_EXCEPTION = "cn.dev33.satoken.exception.SaTokenException";
    private final Map<String, ErrorInfo> internalErrorMapping = new HashMap<>();

    private final Map<String, ErrorInfo> configuredErrorMapping = new HashMap<>();

    private final ErrorInfoProvider errorInfoProvider;

    public ErrorInfoProcessorImpl(final Map<String, ErrorInfo> errorMapping,
                                  ObjectProvider<ErrorInfoProvider> errorInfoProvider) {

        if (!CollectionUtils.isEmpty(errorMapping)) {
            this.configuredErrorMapping.putAll(errorMapping);
        }
        final Map<String, ErrorInfo> internal = internalErrorMapping();
        if (!CollectionUtils.isEmpty(internal)) {
            this.internalErrorMapping.putAll(internal);
        }
        this.errorInfoProvider = errorInfoProvider.getIfAvailable() == null ?
                (t) -> null : errorInfoProvider.getIfAvailable();
    }

    @Override
    @NotNull
    public RuntimeErrorInfo getRuntimeErrorInfo(@NotNull Throwable t) {
        final RuntimeErrorInfo providerErrorInfo = errorInfoProvider.getRuntimeErrorInfo(t);
        if (providerErrorInfo != null) {
            return providerErrorInfo;
        }

        t = getOriginalThrowable(t);
        final ErrorInfo errorInfo = this.getErrorInfo(t.getClass());
        final RuntimeErrorInfo runtimeErrorInfo = new RuntimeErrorInfo(errorInfo);
        String message = ErrorMessageSource.getMessage(errorInfo, t);
        if (StrUtil.isNotEmpty(message)) {
            runtimeErrorInfo.setMessage(message);
        }
        return runtimeErrorInfo;
    }

    @Override
    public String getMessage(@NotNull Throwable t) {
        return getRuntimeErrorInfo(t).getMessage();
    }

    @NotNull
    public ErrorInfo getErrorInfo(@NotNull final Class<? extends Throwable> clazz) {
        if (this.configuredErrorMapping.containsKey(clazz.getName())) {
            return this.configuredErrorMapping.get(clazz.getName());
        }
        final ErrorCode errorCode = AnnotatedElementUtils.findMergedAnnotation(clazz, ErrorCode.class);
        if (errorCode != null) {
            return ErrorInfo.of(errorCode);
        }
        final ResponseStatus responseStatus = AnnotationUtils.findAnnotation(clazz, ResponseStatus.class);
        if (null != responseStatus) {
            return ErrorInfo.of(responseStatus.code().name(),
                    responseStatus.code().value());
        }
        if (internalErrorMapping.containsKey(clazz.getName())) {
            return internalErrorMapping.get(clazz.getName());
        }
        return ErrorInfo.of(HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Override
    @Nullable
    public String getTraceMessage(final Throwable e) {
        if (e.getCause() instanceof InvalidFormatException) {
            final InvalidFormatException cause = (InvalidFormatException) e.getCause();
            return ErrorMessageSource.getMessage(
                    "RUNTIME_PARAM_INVALID_FORMAT", new Object[]{cause.getValue()});
        }
        if (e instanceof MethodArgumentTypeMismatchException) {
            final MethodArgumentTypeMismatchException ec = (MethodArgumentTypeMismatchException) e;
            return ErrorMessageSource.getMessage(
                    "RUNTIME_PARAM_INVALID_TYPE", new Object[]{ec.getName(), ec.getValue()});
        }
        if (e instanceof MethodArgumentNotValidException) {
            final MethodArgumentNotValidException ec = (MethodArgumentNotValidException) e;
            final List<FieldError> fieldErrors = ec.getBindingResult().getFieldErrors();
            final StringJoiner sb = new StringJoiner("; ");
            for (final FieldError fieldError : fieldErrors) {
                final String message = ErrorMessageSource.getMessage(
                        "RUNTIME_PARAM_INVALID", new Object[]{fieldError.getField(),
                                fieldError.getRejectedValue(),
                                fieldError.getDefaultMessage()});
                sb.add(message);
            }
            return sb.toString();
        }
        return e.getMessage();
    }

    @UnmodifiableView
    public Map<String, ErrorInfo> getConfiguredErrorMapping() {
        return Collections.unmodifiableMap(this.configuredErrorMapping);
    }

    private Throwable getOriginalThrowable(Throwable t) {
        if (t.getCause() instanceof InvalidFormatException) {
            return t.getCause();
        }
        else if (null != t.getCause() && SA_EXCEPTION.equals(t.getClass().getName())
                && !SA_EXCEPTION.equals(t.getCause().getClass().getName())) {
            final Class<? extends Throwable> aClass = ReflectUtils.classForName(SA_EXCEPTION);
            if (aClass.isAssignableFrom(t.getCause().getClass())) {
                return t.getCause();
            }
        }
        return t;
    }

    private static Map<String, ErrorInfo> internalErrorMapping() {
        final Properties props = IoUtils.readProperties(
                ErrorInfoProcessorImpl.class.getResourceAsStream("/default-errors-mappings" +
                        ".properties"));
        Map<String, String> mapping = new HashMap<>(props.size());
        for (String propertyName : props.stringPropertyNames()) {
            mapping.put(propertyName, props.getProperty(propertyName));
        }
        return ErrorInfoProcessor.convert(mapping);
    }
}