package cn.javaer.jany.ebean.expression;

import io.ebean.Expression;
import io.ebean.ExpressionFactory;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static cn.javaer.jany.ebean.expression.ExprOperator.eq;

/**
 * @author cn-src
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryExpr {
    ExprOperator value() default eq;

    String property() default "";

    ExprValueType valueType() default ExprValueType.DEFAULT;

    QueryExpr DEFAULT = new QueryExpr() {
        @Override
        public Class<? extends Annotation> annotationType() {
            return QueryExpr.class;
        }

        @Override
        public ExprOperator value() {
            return eq;
        }

        @Override
        public String property() {
            return "";
        }

        @Override
        public ExprValueType valueType() {
            return ExprValueType.DEFAULT;
        }
    };

    interface ExprFunction {
        Expression apple(ExpressionFactory factory, String property, Object value);
    }
}