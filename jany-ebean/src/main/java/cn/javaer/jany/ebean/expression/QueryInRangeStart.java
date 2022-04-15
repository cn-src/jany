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
@QueryExpr(value = ExprOperator.inRange, valueType = ExprValueType.RANGE_START)
public @interface QueryInRangeStart {
    String property() default "";
}