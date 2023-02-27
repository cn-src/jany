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

package cn.javaer.jany.spring.format;

import cn.javaer.jany.format.DateTimeFormat;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Parser;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author cn-src
 */
public final class DateTimeParser implements Parser<LocalDateTime> {
    private final DateTimeFormat annotation;

    public DateTimeParser(final DateTimeFormat annotation) {
        this.annotation = annotation;
    }

    @Override
    public @NotNull LocalDateTime parse(final String text, @NonNull final Locale locale) {
        if (this.annotation.datePattern().length() == text.length()) {
            final LocalDate date = LocalDate.parse(text,
                DateTimeFormatter.ofPattern(this.annotation.datePattern(), locale));
            return DateTimeFormat.Formatter.format(date, this.annotation);
        }
        return LocalDateTime.parse(text,
            DateTimeFormatter.ofPattern(this.annotation.dateTimePattern(), locale));
    }
}