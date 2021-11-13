//package cn.javaer.jany.spring.exception;
//
//import cn.javaer.jany.exception.ErrorInfo;
//import org.springframework.http.HttpStatus;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author cn-src
// */
//public class ErrorMappings {
//    private ErrorMappings() {
//    }
//
//    private static final Map<String, ErrorInfo> INTERNAL = new HashMap<>();
//
//    static {
//        // start security
//        INTERNAL.put(
//            "org.springframework.security.core.userdetails.UsernameNotFoundException",
//            ErrorInfo.of("LOGIN_ERROR_BAD_CREDENTIALS", 401)
//        );
//        INTERNAL.put(
//            "org.springframework.security.authentication.BadCredentialsException",
//            ErrorInfo.of("LOGIN_ERROR_BAD_CREDENTIALS", 401,
//                HttpStatus.UNAUTHORIZED.getReasonPhrase())
//        );
//        INTERNAL.put(
//            "org.springframework.security.authentication.DisabledException",
//            ErrorInfo.of("LOGIN_ERROR_DISABLED", 401,
//                HttpStatus.UNAUTHORIZED.getReasonPhrase())
//        );
//        // end security
//
//        INTERNAL.put(
//            "org.springframework.web.HttpRequestMethodNotSupportedException",
//            DefinedErrorInfo.of(HttpStatus.METHOD_NOT_ALLOWED)
//        );
//
//        INTERNAL.put(
//            "org.springframework.web.HttpMediaTypeNotSupportedException",
//            DefinedErrorInfo.of(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//        );
//
//        INTERNAL.put(
//            "org.springframework.web.HttpMediaTypeNotAcceptableException",
//            DefinedErrorInfo.of(HttpStatus.NOT_ACCEPTABLE)
//        );
//
//        INTERNAL.put(
//            "org.springframework.web.bind.MissingPathVariableException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//
//        INTERNAL.put(
//            "org.springframework.web.bind.MissingServletRequestParameterException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//
//        INTERNAL.put(
//            "org.springframework.web.bind.ServletRequestBindingException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//
//        INTERNAL.put(
//            "org.springframework.beans.TypeMismatchException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//
//        INTERNAL.put(
//            "org.springframework.http.converter.HttpMessageNotReadableException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//
//        INTERNAL.put(
//            "org.springframework.web.bind.MethodArgumentNotValidException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//
//        INTERNAL.put(
//            "org.springframework.web.multipart.support.MissingServletRequestPartException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//
//        INTERNAL.put(
//            "org.springframework.validation.BindException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//
//        INTERNAL.put(
//            "org.springframework.web.servlet.NoHandlerFoundException",
//            DefinedErrorInfo.of(HttpStatus.NOT_FOUND));
//
//        INTERNAL.put(
//            "org.springframework.web.context.request.async.AsyncRequestTimeoutException",
//            DefinedErrorInfo.of(HttpStatus.SERVICE_UNAVAILABLE));
//
//        INTERNAL.put("javax.validation.ConstraintViolationException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//
//        INTERNAL.put(
//            "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException",
//            DefinedErrorInfo.of(HttpStatus.BAD_REQUEST));
//    }
//
//    public static DefinedErrorInfo getErrorInfo(final String exceptionName) {
//        return INTERNAL.get(exceptionName);
//    }
//
//    public static DefinedErrorInfo getErrorInfo(final String exceptionName,
//                                                final DefinedErrorInfo defaultDefinedErrorInfo) {
//        return INTERNAL.getOrDefault(exceptionName, defaultDefinedErrorInfo);
//    }
//
//    public static boolean containsError(final String exceptionName) {
//        return INTERNAL.containsKey(exceptionName);
//    }
//}