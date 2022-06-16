package cn.javaer.jany.exception;

import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

/**
 * 错误信息。
 *
 * @author cn-src
 */
@Value
public class ErrorInfo implements Comparable<ErrorInfo> {
    String error;

    int status;

    String doc;

    ErrorInfo(final String error, final int status) {
        Objects.requireNonNull(error);
        this.error = error;
        this.status = status;
        this.doc = "Unknown";
    }

    ErrorInfo(final String error, final int status, String doc) {
        Objects.requireNonNull(error);
        this.error = error;
        this.status = status;
        this.doc = doc;
    }

    public static ErrorInfo of(final ErrorCode errorCode) {
        return new ErrorInfo(errorCode.error(), errorCode.status(), errorCode.doc());
    }

    public static ErrorInfo of(final String error, final int status) {
        return new ErrorInfo(error, status);
    }

    public static ErrorInfo of401(final String error, final int status) {
        return new ErrorInfo(error, status);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ErrorInfo that = (ErrorInfo) o;
        return Objects.equals(this.error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.error);
    }

    @Override
    public int compareTo(final @NotNull ErrorInfo errorInfo) {
        return Comparator.comparing(ErrorInfo::getStatus, Integer::compare)
            .thenComparing(ErrorInfo::getError, String::compareTo)
            .compare(this, errorInfo);
    }

    // ---- 40x

    public static final String BAD_REQUEST = "BAD_REQUEST";

    public static final String UNAUTHORIZED = "UNAUTHORIZED";

    public static final String FORBIDDEN = "FORBIDDEN";

    public static final String NOT_FOUND = "NOT_FOUND";

    // ---- 50x

    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    /**
     * 登录错误。
     */
    public static final String LOGIN_ERROR = "LOGIN_ERROR";

    /**
     * 登录错误，用户名或密码错误。
     */
    public static final String LOGIN_ERROR_BAD_CREDENTIALS = "LOGIN_ERROR_BAD_CREDENTIALS";

    /**
     * 登录错误，账户被禁用。
     */
    public static final String LOGIN_ERROR_DISABLED = "LOGIN_ERROR_DISABLED";

    /**
     * 登录已过期。
     */
    public static final String TOKEN_EXPIRED = "LOGIN_ERROR_EXPIRED";
}