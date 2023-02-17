package cn.javaer.jany.ebean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果值为空，则标识为未更新，自动调用 Model#markPropertyUnset。
 *
 * @author cn-src
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnsetIfEmpty {
}