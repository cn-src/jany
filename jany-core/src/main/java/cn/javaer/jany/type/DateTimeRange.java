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

package cn.javaer.jany.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * @author cn-src
 */
public final class DateTimeRange {
    private final LocalDateTime start;

    private final LocalDateTime end;

    @SuppressWarnings("unused")
    @JsonCreator
    @ConstructorProperties({"start", "end"})
    DateTimeRange(final LocalDateTime start, final LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 从当天开始时间到当前时间.
     *
     * @return DateTimeRange
     */
    public static DateTimeRange startNowOfDay() {
        return new DateTimeRange(LocalDate.now().atStartOfDay(), LocalDateTime.now());
    }

    /**
     * 从当天减去指定天数的开始时间，到当前时间.
     *
     * @param minusDays 减去指定天数
     *
     * @return DateTimeRange
     */
    public static DateTimeRange startNowOfDay(final int minusDays) {
        return new DateTimeRange(LocalDate.now().minusDays(minusDays).atStartOfDay(),
            LocalDateTime.now());
    }

    /**
     * 从当月开始时间到当前时间.
     *
     * @return DateTimeRange
     */
    public static DateTimeRange startNowOfMonth() {
        return new DateTimeRange(YearMonth.now().atDay(1).atStartOfDay(), LocalDateTime.now());
    }

    /**
     * 从当月减去指定月数的开始时间，到当前时间.
     *
     * @param minusMonths 减去指定月数
     *
     * @return DateTimeRange
     */
    public static DateTimeRange startNowOfMonth(final int minusMonths) {
        return new DateTimeRange(YearMonth.now().minusMonths(minusMonths).atDay(1).atStartOfDay(),
            LocalDateTime.now());
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }
}