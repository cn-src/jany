package cn.javaer.jany.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.javaer.jany.validation.AllOrNoneEmpty.List;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 一组数据要么同时为空，要么同时不为空。
 *
 * @author cn-src
 */
@Documented
@Constraint(validatedBy = AllOrNoneEmptyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface AllOrNoneEmpty {

    String message() default "{jany.validation.constraints.NotEmptyGroup.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] value();

    @Target({ElementType.TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        AllOrNoneEmpty[] value();
    }
}