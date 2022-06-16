package cn.javaer.jany.exception;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 错误码注解。
 *
 * @author cn-src
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface ErrorCode {

    /**
     * 错误码。
     */
    String error();

    /**
     * 状态码。
     */
    int status() default 500;

    /**
     * 用于 swagger/openapi 的文档描述信息。
     */
    String doc() default "";

    // ---- 40x

    String BAD_REQUEST = "BAD_REQUEST";
    String UNAUTHORIZED = "UNAUTHORIZED";
    String FORBIDDEN = "FORBIDDEN";
    String NOT_FOUND = "NOT_FOUND";

    // ---- 50x

    String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    /**
     * 登录错误。
     */
    String LOGIN_ERROR = "LOGIN_ERROR";

    /**
     * 登录错误，用户名或密码错误。
     */
    String LOGIN_ERROR_BAD_CREDENTIALS = "LOGIN_ERROR_BAD_CREDENTIALS";

    /**
     * 登录错误，账户被禁用。
     */
    String LOGIN_ERROR_DISABLED = "LOGIN_ERROR_DISABLED";

    /**
     * 登录已过期。
     */
    String TOKEN_EXPIRED = "LOGIN_ERROR_EXPIRED";
}