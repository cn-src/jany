package cn.javaer.jany.format;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cn-src
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface StringFormat {

    boolean apply() default true;

    boolean trim() default true;

    boolean emptyToNull() default true;

    interface Formatter {
        static String format(final String str, final StringFormat format) {
            String value = str;
            if (null != format && format.apply()) {
                if (format.trim()) {
                    value = value.trim();
                }
                if (format.emptyToNull() && value.isEmpty()) {
                    return null;
                }
            }
            return value;
        }
    }
}