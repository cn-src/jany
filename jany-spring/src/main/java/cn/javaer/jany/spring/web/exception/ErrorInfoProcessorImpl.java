package cn.javaer.jany.spring.web.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.javaer.jany.exception.ErrorCode;
import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.util.IoUtils;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringJoiner;

/**
 * @author cn-src
 */
public class ErrorInfoProcessorImpl implements ErrorInfoProcessor {

    private static final String SA_TOKEN_NOT_LOGIN = "cn.dev33.satoken.exception.NotLoginException";

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
        this.errorInfoProvider = errorInfoProvider.getIfAvailable(() -> null);
    }

    @Override
    @NotNull
    public ErrorInfo getErrorInfo(@NotNull final Throwable t) {
        if (errorInfoProvider.getErrorInfo(t) != null) {
            return errorInfoProvider.getErrorInfo(t);
        }

        Class<? extends Throwable> clazz = t.getClass();
        if (t.getCause() instanceof InvalidFormatException) {
            clazz = InvalidFormatException.class;
        }
        if (clazz.getName().equals(SA_TOKEN_NOT_LOGIN)) {
            NotLoginException e = (NotLoginException) t;
            if (NotLoginException.NOT_TOKEN.equals(e.getType())) {
                return ErrorInfo.of401(ErrorInfo.UNAUTHORIZED);
            }
            if (NotLoginException.INVALID_TOKEN.equals(e.getType())) {
                return ErrorInfo.of401(ErrorInfo.LOGIN_ERROR_BAD_CREDENTIALS);
            }
            if (NotLoginException.TOKEN_TIMEOUT.equals(e.getType())) {
                return ErrorInfo.of401(ErrorInfo.TOKEN_EXPIRED);
            }
            if (NotLoginException.BE_REPLACED.equals(e.getType())) {
                return ErrorInfo.of401(ErrorInfo.SESSION_OUT_REPLACED);
            }
            if (NotLoginException.KICK_OUT.equals(e.getType())) {
                return ErrorInfo.of401(ErrorInfo.SESSION_OUT_KICKED);
            }
            return ErrorInfo.of401(ErrorInfo.UNAUTHORIZED);
        }

        return this.getErrorInfo(clazz);
    }

    @Override
    @NotNull
    public ErrorInfo getErrorInfo(@NotNull final Class<? extends Throwable> clazz) {
        if (this.configuredErrorMapping.containsKey(clazz.getName())) {
            return this.configuredErrorMapping.get(clazz.getName());
        }
        final ErrorCode errorCode = AnnotatedElementUtils.findMergedAnnotation(clazz,
            ErrorCode.class);
        if (errorCode != null) {
            return ErrorInfo.of(errorCode);
        }
        final ResponseStatus responseStatus = AnnotationUtils.findAnnotation(
            clazz, ResponseStatus.class);
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
    public String getRuntimeMessage(final Throwable e) {
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
        return null;
    }

    @UnmodifiableView
    public Map<String, ErrorInfo> getConfiguredErrorMapping() {
        return Collections.unmodifiableMap(this.configuredErrorMapping);
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