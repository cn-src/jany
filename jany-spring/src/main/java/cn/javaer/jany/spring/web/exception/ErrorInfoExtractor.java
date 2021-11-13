package cn.javaer.jany.spring.web.exception;

import cn.hutool.core.util.ArrayUtil;
import cn.javaer.jany.exception.ErrorCode;
import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.util.IoUtils;
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
import java.util.Properties;
import java.util.StringJoiner;

/**
 * @author cn-src
 */
public class ErrorInfoExtractor {

    private final Map<String, ErrorInfo> internalErrorMapping = new HashMap<>();
    private final Map<String, ErrorInfo> configuredErrorMapping = new HashMap<>();

    public ErrorInfoExtractor(final Map<String, ErrorInfo> errorMapping) {

        if (!CollectionUtils.isEmpty(errorMapping)) {
            this.configuredErrorMapping.putAll(errorMapping);
        }
        final Map<String, ErrorInfo> internal = internalErrorMapping();
        if (!CollectionUtils.isEmpty(internal)) {
            this.internalErrorMapping.putAll(internal);
        }
    }

    public Map<String, ErrorInfo> getControllersErrorMapping(final Collection<Object> controllers) {
        final Map<String, ErrorInfo> result = new HashMap<>(20);
        for (final Object ctr : controllers) {
            final Method[] methods = ctr.getClass().getDeclaredMethods();
            for (final Method method : methods) {
                if (null != AnnotationUtils.findAnnotation(method, RequestMapping.class)) {
                    final Class<?>[] exceptionTypes = method.getExceptionTypes();
                    if (exceptionTypes != null && exceptionTypes.length > 0) {
                        for (final Class<?> type : exceptionTypes) {
                            @SuppressWarnings("unchecked")
                            final Class<? extends Throwable> t = (Class<? extends Throwable>) type;
                            final ErrorInfo extract = this.getErrorInfo(t);
                            result.put(t.getName(), extract);
                        }
                    }
                }
            }
        }
        return result;
    }
//
//    public RuntimeErrorInfo getRuntimeErrorInfo(final Throwable t, final boolean
//    includeTraceMessage) {
//        final RuntimeErrorInfo errorInfo = new RuntimeErrorInfo(this.getErrorInfo(t));
//        errorInfo.set
////        if (includeMessage) {
////            final String msg = this.getMessage(t);
////            if (StringUtils.hasLength(msg)) {
////                errorInfo.setMessage(msg);
////            }
////            else {
////                final String message = ErrorMessageSource.getMessage(errorInfo.getError());
////                if (StringUtils.hasLength(message)) {
////                    errorInfo.setMessage(message);
////                }
////            }
////        }
////        else {
////            errorInfo.setMessage(null);
////        }
//        return errorInfo;
//    }

//    public DefinedErrorInfo getErrorInfoWithMessage(final Class<? extends Throwable> clazz) {
//        final DefinedErrorInfo errorInfo = this.getErrorInfo(clazz);
//        final String message = ErrorMessageSource.getMessage(errorInfo.getError());
//        if (StringUtils.hasLength(message)) {
//            return errorInfo.withMessage(message);
//        }
//        return errorInfo;
//    }

    @NotNull
    public ErrorInfo getErrorInfo(final Throwable t) {
        Class<? extends Throwable> clazz = t.getClass();
        if (t.getCause() instanceof InvalidFormatException) {
            clazz = InvalidFormatException.class;
        }
        return this.getErrorInfo(clazz);
    }

    @NotNull
    public ErrorInfo getErrorInfo(final Class<? extends Throwable> clazz) {
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

    public static Map<String, ErrorInfo> convert(Map<String, String> mapping) {

        if (!CollectionUtils.isEmpty(mapping)) {
            Map<String, ErrorInfo> errorInfoMap = new HashMap<>(mapping.size());
            for (final Map.Entry<String, String> entry : mapping.entrySet()) {
                final String value = entry.getValue();
                if (StringUtils.hasText(value)) {
                    final String[] split = StringUtils.split(value, ",");
                    if (ArrayUtil.length(split) != 2) {
                        // TODO
                        throw new RuntimeException(value);
                    }
                    final ErrorInfo errorInfo = ErrorInfo.of(split[1].trim(),
                        Integer.parseInt(split[0].trim()));
                    errorInfoMap.put(entry.getKey(), errorInfo);
                }
            }
            return Collections.unmodifiableMap(errorInfoMap);
        }
        return Collections.emptyMap();
    }

    private static Map<String, ErrorInfo> internalErrorMapping() {
        final Properties props = IoUtils.readProperties(
            ErrorInfoExtractor.class.getResourceAsStream("/default-errors-mappings.properties"));
        Map<String, String> mapping = new HashMap<>(props.size());
        for (String propertyName : props.stringPropertyNames()) {
            mapping.put(propertyName, props.getProperty(propertyName));
        }
        return convert(mapping);
    }
}