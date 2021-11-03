package cn.javaer.jany.spring.web.exception;

import cn.javaer.jany.spring.exception.Error;
import cn.javaer.jany.spring.exception.DefinedErrorInfo;
import cn.javaer.jany.spring.exception.ErrorMappings;
import cn.javaer.jany.spring.exception.ErrorMessageSource;
import cn.javaer.jany.spring.exception.RuntimeErrorInfo;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author cn-src
 */
public class ErrorInfoExtractor {

    private final Map<String, DefinedErrorInfo> configuredErrorMapping = new HashMap<>();

    public ErrorInfoExtractor(final Map<String, DefinedErrorInfo> errorMapping) {

        if (!CollectionUtils.isEmpty(errorMapping)) {
            this.configuredErrorMapping.putAll(errorMapping);
        }
    }

    public Map<String, DefinedErrorInfo> getControllersErrorMapping(final Collection<Object> controllers) {
        final Map<String, DefinedErrorInfo> result = new HashMap<>(20);
        for (final Object ctr : controllers) {
            final Method[] methods = ctr.getClass().getDeclaredMethods();
            for (final Method method : methods) {
                if (null != AnnotationUtils.findAnnotation(method, RequestMapping.class)) {
                    final Class<?>[] exceptionTypes = method.getExceptionTypes();
                    if (exceptionTypes != null && exceptionTypes.length > 0) {
                        for (final Class<?> type : exceptionTypes) {
                            @SuppressWarnings("unchecked")
                            final Class<? extends Throwable> t = (Class<? extends Throwable>) type;
                            final DefinedErrorInfo extract = this.getErrorInfoWithMessage(t);
                            result.put(t.getName(), extract);
                        }
                    }
                }
            }
        }
        return result;
    }

    public RuntimeErrorInfo getRuntimeErrorInfo(final Throwable t, final boolean includeMessage) {
        final RuntimeErrorInfo errorInfo = new RuntimeErrorInfo(this.getErrorInfo(t));
        if (includeMessage) {
            final String msg = this.getMessage(t);
            if (StringUtils.hasLength(msg)) {
                errorInfo.setMessage(msg);
            }
            else {
                final String message = ErrorMessageSource.getMessage(errorInfo.getError());
                if (StringUtils.hasLength(message)) {
                    errorInfo.setMessage(message);
                }
            }
        }
        else {
            errorInfo.setMessage(null);
        }
        return errorInfo;
    }

    public DefinedErrorInfo getErrorInfoWithMessage(final Class<? extends Throwable> clazz) {
        final DefinedErrorInfo errorInfo = this.getErrorInfo(clazz);
        final String message = ErrorMessageSource.getMessage(errorInfo.getError());
        if (StringUtils.hasLength(message)) {
            return errorInfo.withMessage(message);
        }
        return errorInfo;
    }

    @NotNull
    public DefinedErrorInfo getErrorInfo(final Throwable t) {
        Class<? extends Throwable> clazz = t.getClass();
        if (t.getCause() instanceof InvalidFormatException) {
            clazz = InvalidFormatException.class;
        }
        return this.getErrorInfo(clazz);
    }

    @NotNull
    public DefinedErrorInfo getErrorInfo(final Class<? extends Throwable> clazz) {
        if (this.configuredErrorMapping.containsKey(clazz.getName())) {
            return this.configuredErrorMapping.get(clazz.getName());
        }
        final Error error = AnnotatedElementUtils.findMergedAnnotation(clazz, Error.class);
        if (error != null) {
            return DefinedErrorInfo.of(error);
        }
        final ResponseStatus responseStatus = AnnotationUtils.findAnnotation(
            clazz, ResponseStatus.class);
        if (null != responseStatus) {
            return DefinedErrorInfo.of(responseStatus);
        }
        if (ErrorMappings.containsError(clazz.getName())) {
            return ErrorMappings.getErrorInfo(clazz.getName());
        }
        return DefinedErrorInfo.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Nullable
    private String getMessage(final Throwable e) {
        if (e.getCause() instanceof InvalidFormatException) {
            final InvalidFormatException cause = (InvalidFormatException) e.getCause();
            return ErrorMessageSource.getMessage(
                "PARAM_INVALID_FORMAT", new Object[]{cause.getValue()});
        }
        if (e instanceof MethodArgumentTypeMismatchException) {
            final MethodArgumentTypeMismatchException ec = (MethodArgumentTypeMismatchException) e;
            return ErrorMessageSource.getMessage(
                "PARAM_INVALID_TYPE", new Object[]{ec.getName(), ec.getValue()});
        }
        if (e instanceof MethodArgumentNotValidException) {
            final MethodArgumentNotValidException ec = (MethodArgumentNotValidException) e;
            final List<FieldError> fieldErrors = ec.getBindingResult().getFieldErrors();
            final StringJoiner sb = new StringJoiner("; ");
            for (final FieldError fieldError : fieldErrors) {
                final String message = ErrorMessageSource.getMessage(
                    "PARAM_INVALID", new Object[]{fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()});
                sb.add(message);
            }
            return sb.toString();
        }
        return null;
    }

    @UnmodifiableView
    public Map<String, DefinedErrorInfo> getConfiguredErrorMapping() {
        return Collections.unmodifiableMap(this.configuredErrorMapping);
    }
}