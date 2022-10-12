package cn.javaer.jany.ebean;

import cn.javaer.jany.model.Sort;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cn-src
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SortBy {
    Sort.Direction direction() default Sort.Direction.ASC;
}