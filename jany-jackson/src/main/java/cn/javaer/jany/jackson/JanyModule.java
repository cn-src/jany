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

package cn.javaer.jany.jackson;

import cn.javaer.jany.util.TimeUtils;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class JanyModule extends SimpleModule {
    public JanyModule() {
        // @formatter:off
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(TimeUtils.DATE_TIME_FORMATTER));
        addSerializer(LocalDate.class, new LocalDateSerializer(TimeUtils.DATE_FORMATTER));
        addSerializer(LocalTime.class, new LocalTimeSerializer(TimeUtils.TIME_FORMATTER));

        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(TimeUtils.DATE_TIME_FORMATTER));
        addDeserializer(LocalDate.class, new LocalDateDeserializer(TimeUtils.DATE_FORMATTER));
        addDeserializer(LocalTime.class, new LocalTimeDeserializer(TimeUtils.TIME_FORMATTER));
        // @formatter:on
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.appendAnnotationIntrospector(JanyJacksonAnnotationIntrospector.INSTANCE);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String getModuleName() {
        return "JanyModule";
    }
}