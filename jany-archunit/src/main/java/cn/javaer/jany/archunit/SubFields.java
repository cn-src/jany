package cn.javaer.jany.archunit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检测被注解 {@link SubFields} 的类的字段是否是 {@link SubFields#value()} 声明的类的字段的子集.
 * <p>
 * 判断时忽略静态字段和被 {@link org.springframework.data.annotation.Transient} 标注的字段.
 * </p>
 *
 * @author cn-src
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SubFields {
    Class<?> value();
}