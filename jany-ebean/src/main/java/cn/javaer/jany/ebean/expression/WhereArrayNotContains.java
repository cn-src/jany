package cn.javaer.jany.ebean.expression;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cn-src
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@WhereExpression(value = Operator.arrayNotContains)
public @interface WhereArrayNotContains {
    String property() default "";
}