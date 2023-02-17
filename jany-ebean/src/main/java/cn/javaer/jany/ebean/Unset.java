package cn.javaer.jany.ebean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果值为空，则标识为未更新，自动调用 Model#markPropertyUnset。
 *
 * @author cn-src
 * @see JanyBeanPersistController
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unset {

    /**
     * 只有属性值为空，才标识为未更新。
     *
     * @return ifEmpty
     */
    boolean onlyEmpty() default false;
}