/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
     * 它接受一个字符串并返回一个 LocalDateTime 对象
     *
     * @param str 要解析的字符串。
     *
     * @return 一个 LocalDateTime 对象
     */
    static LocalDateTime parseDateTime(String str) {
        return LocalDateTime.parse(str, TimeUtils.DATE_TIME_FORMATTER);
    }

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