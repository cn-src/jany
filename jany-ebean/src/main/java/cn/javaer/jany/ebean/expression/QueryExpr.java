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
 * 使用样例：
 * <pre>{@code
 * @Data
 * @ParameterObject
 * public class UserQuery {
 *
 *     @QueryExpr(ExprOperator.contains)
 *     @Schema(description = "姓名")
 *     private String name;
 *
 *     @QueryExpr(ExprOperator.contains)
 *     @Schema(description = "手机号")
 *     private String mobile;
 *
 *     @QueryExpr(ExprOperator.contains)
 *     @Schema(description = "邮箱")
 *     private String email;
 *
 *     @QueryExpr(value = ExprOperator.inRange, property = "createTime",valueType = ExprValueType.RANGE_START)
 *     @Schema(description = "查询开始时间")
 *     private LocalDate startCreateTime;
 *
 *     @QueryExpr(value = ExprOperator.inRange, property = "createTime",valueType = ExprValueType.RANGE_END)
 *     @Schema(description = "查询结束时间")
 *     private LocalDate endCreateTime;
 *
 *     @Schema(description = "启用")
 *     private Boolean enabled;
 * }
 * }</pre>
 *
 * @author cn-src
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryExpr {
    ExprOperator value() default eq;

    /**
     * 不配置属性名称时，默认取样例对象的字段名称，当 valueType 为 RANGE_* 时，则必须配置为实体类的属性名称。
     *
     * @return 属性名称
     */
    String property() default "";

    /**
     * 当使用 inRange, between 这样的范围操作时需要标注当前字段是作为范围的开始值，还是结束值。
     *
     * @return ExprValueType
     */
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