//package cn.javaer.jany.spring.exception;
//
//import lombok.Value;
//import lombok.With;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.util.Comparator;
//import java.util.Objects;
//
///**
// * @author cn-src
// */
//@Value
//public class DefinedErrorInfo implements Comparable<DefinedErrorInfo> {
//    String error;
//    int status;
//    @With String message;
//
//    DefinedErrorInfo(final String error, final int status, final String message) {
//        Objects.requireNonNull(error);
//        this.error = error;
//        this.status = status;
//        this.message = message;
//    }
//
//    public static DefinedErrorInfo of(final Error error) {
//        return new DefinedErrorInfo(error.error(), error.status(), error.message());
//    }
//
//    @Deprecated
//    public static DefinedErrorInfo of(final Error error, final String message) {
//        return new DefinedErrorInfo(error.error(), error.status(), message);
//    }
//
//    public static DefinedErrorInfo of(final ResponseStatus responseStatus) {
//        final HttpStatus httpStatus = responseStatus.code() == HttpStatus.INTERNAL_SERVER_ERROR ?
//            responseStatus.value() : responseStatus.code();
//
//        if (responseStatus.reason().isEmpty()) {
//            return DefinedErrorInfo.of(httpStatus);
//        }
//        return DefinedErrorInfo.of(httpStatus, responseStatus.reason());
//    }
//
//    @Deprecated
//    public static DefinedErrorInfo of(final ResponseStatus responseStatus, final String message) {
//        final HttpStatus httpStatus = responseStatus.code() == HttpStatus.INTERNAL_SERVER_ERROR ?
//            responseStatus.value() : responseStatus.code();
//
//        return DefinedErrorInfo.of(httpStatus, message);
//    }
//
//    public static DefinedErrorInfo of(final String error, final int status,
//                                      final String message) {
//        return new DefinedErrorInfo(error, status, message);
//    }
//
//    public static DefinedErrorInfo of(final String error, final int status) {
//        return new DefinedErrorInfo(error, status, "No message");
//    }
//
//    public static DefinedErrorInfo of(final HttpStatus httpStatus) {
//        return new DefinedErrorInfo(httpStatus.name(), httpStatus.value(),
//            httpStatus.getReasonPhrase());
//    }
//
//    @Deprecated
//    public static DefinedErrorInfo of(final HttpStatus httpStatus, final String message) {
//        return new DefinedErrorInfo(httpStatus.name(), httpStatus.value(), message);
//    }
//
//    @Deprecated
//    public static DefinedErrorInfo of(final String error, final HttpStatus httpStatus) {
//        return new DefinedErrorInfo(error, httpStatus.value(),
//            httpStatus.getReasonPhrase());
//    }
//
//    @Deprecated
//    public static DefinedErrorInfo of(final DefinedErrorInfo errorInfo, final String message) {
//        return new DefinedErrorInfo(errorInfo.error, errorInfo.status, message);
//    }
//
//    @Override
//    public boolean equals(final Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || this.getClass() != o.getClass()) {
//            return false;
//        }
//        final DefinedErrorInfo that = (DefinedErrorInfo) o;
//        return Objects.equals(this.error, that.error);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(this.error);
//    }
//
//    @Override
//    public int compareTo(final @NotNull DefinedErrorInfo errorInfo) {
//        return Comparator.comparing(DefinedErrorInfo::getStatus, Integer::compare)
//            .thenComparing(DefinedErrorInfo::getError, String::compareTo)
//            .compare(this, errorInfo);
//    }
//}