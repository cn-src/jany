package cn.javaer.jany.format;

import cn.javaer.jany.util.TimeUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 日期格式化注解，支持填充没有时间的日期。
 *
 * @author cn-src
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface DateTimeFormat {

    /**
     * 日期的格式
     */
    String datePattern() default TimeUtils.DATE_PATTERN;

    /**
     * 日期时间的格式
     */
    String dateTimePattern() default TimeUtils.DATE_TIME_PATTERN;

    /**
     * 在只有日期的时候, 填充时间的方式
     */
    Time time();

    enum Time {

        /**
         * 填充当天最小时间
         * <p>
         * {@link LocalTime#MIN}
         */
        MIN,

        /**
         * 填充当天最大时间
         * <p>
         * {@link LocalTime#MAX}
         */
        MAX
    }

    interface Conversion {

        /**
         * 日期转换日期时间
         *
         * @param localDate LocalDate
         * @param format DateFillFormat
         *
         * @return LocalDateTime
         */
        static LocalDateTime conversion(final LocalDate localDate,
                                        final DateTimeFormat format) {

            switch (format.time()) {
                case MIN:
                    return localDate.atTime(LocalTime.MIN);
                case MAX:
                    return localDate.atTime(LocalTime.MAX);
                default:
                    throw new IllegalStateException();
            }
        }
    }
}