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
     * 错误消息。
     */
    String message() default "";

    /**
     * 用于 swagger/openapi 的文档描述信息。
     */
    String doc() default "";
}