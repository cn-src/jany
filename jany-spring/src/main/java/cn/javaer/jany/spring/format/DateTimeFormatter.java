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
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.datetime.standard.TemporalAccessorPrinter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * @author cn-src
 */
public class DateTimeFormatter extends EmbeddedValueResolutionSupport
    implements AnnotationFormatterFactory<DateTimeFormat> {

    @Override
    @NonNull
    public Set<Class<?>> getFieldTypes() {
        return Collections.singleton(LocalDateTime.class);
    }

    @Override
    @NonNull
    public Printer<?> getPrinter(final DateTimeFormat annotation,
                                 final @NotNull Class<?> fieldType) {
        return new TemporalAccessorPrinter(
            java.time.format.DateTimeFormatter.ofPattern(annotation.dateTimePattern()));
    }

    @Override
    @NonNull
    public Parser<?> getParser(@NonNull final DateTimeFormat annotation,
                               final @NotNull Class<?> fieldType) {
        return new DateTimeParser(annotation);
    }
}