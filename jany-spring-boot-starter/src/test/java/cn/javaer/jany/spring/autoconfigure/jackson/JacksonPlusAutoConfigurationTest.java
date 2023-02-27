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

package cn.javaer.jany.spring.autoconfigure.jackson;

import cn.javaer.jany.format.DateTimeFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.jooq.JSONB;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class JacksonPlusAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(
            JacksonPlusAutoConfiguration.class, JacksonAutoConfiguration.class));

    @Test
    void auto() throws Exception {
        this.contextRunner.run(context -> {
            final ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
            // language=JSON
            final Demo demo = objectMapper.readValue("{\"dateTime\": \"2020-05-05\"}", Demo.class);
            demo.setStr("val");
            // language=JSON
            demo.setJsonb(JSONB.valueOf("{\"demo\":123}"));
            demo.setLocalDateTime(LocalDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.MIN));
            demo.setLocalDate(LocalDate.of(2020, 1, 1));
            demo.setLocalTime(LocalTime.MIN);
            final String value = objectMapper.writeValueAsString(demo);
            assertThat(demo.dateTime).isEqualTo(LocalDate.parse("2020-05-05").atTime(LocalTime.MAX));
            // language=JSON
            JSONAssert.assertEquals("{\"dateTime\":\"2020-05-05 23:59:59\"," +
                "\"str\":\"val\"," +
                "\"jsonb\":{\"demo\":123}," +
                "\"localDateTime\":\"2020-01-01 00:00:00\"," +
                " \"" +
                "localDate\":\"2020-01-01\"," +
                "\"localTime\":\"00:00:00\"}", value, true);
        });
    }

    @Data
    static class Demo {
        @DateTimeFormat(time = DateTimeFormat.Time.MAX)
        LocalDateTime dateTime;

        String str;

        JSONB jsonb;

        LocalDateTime localDateTime;

        LocalDate localDate;

        LocalTime localTime;
    }
}