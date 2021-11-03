package cn.javaer.jany.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * @author cn-src
 */
public interface TimeUtils {

    String TIME_PATTERN = "HH:mm:ss";
    String DATE_PATTERN = "yyyy-MM-dd";
    String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * 当月的开始时间，例如：2020-06-01 00:00:00.
     *
     * @return LocalDateTime
     */
    static LocalDateTime monthStart() {
        return YearMonth.now().atDay(1).atStartOfDay();
    }

    /**
     * 当月的结束时间，例如：2020-06-01 23:59:59.999999999.
     *
     * @return LocalDateTime
     */
    static LocalDateTime monthEnd() {
        return YearMonth.now().atEndOfMonth().atTime(LocalTime.MAX);
    }
}