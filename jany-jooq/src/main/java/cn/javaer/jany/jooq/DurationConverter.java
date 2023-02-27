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

package cn.javaer.jany.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

import java.time.Duration;

/**
 * @author cn-src
 */
public enum DurationConverter implements Converter<String, Duration> {

    /**
     * 单实例
     */
    INSTANCE;

    private static final long serialVersionUID = 599360862926272439L;

    @Override
    public Duration from(final String db) {

        return null == db ? null : Duration.parse(db);
    }

    @Override
    public String to(final Duration duration) {
        if (null == duration) {
            return null;
        }

        return duration.toString();
    }

    @Override
    public @NotNull Class<String> fromType() {
        return String.class;
    }

    @Override
    public @NotNull Class<Duration> toType() {
        return Duration.class;
    }
}