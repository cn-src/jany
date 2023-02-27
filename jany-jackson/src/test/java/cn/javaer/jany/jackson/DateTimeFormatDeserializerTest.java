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

import cn.javaer.jany.format.DateMaxTime;
import cn.javaer.jany.format.DateMinTime;
import cn.javaer.jany.format.DateTimeFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author cn-src
 */
class DateTimeFormatDeserializerTest {

    @Test
    void deserialize() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.findAndRegisterModules();
        objectMapper.registerModules(new JavaTimeModule(), new JanyModule());
        // language=JSON
        final Demo demo = objectMapper.readValue("{\"dateTime\": \"2020-05-05\",\"dateMinTime\": " +
            "\"2020-02-05\",\"dateMaxTime\": \"2020-03-05\"}", Demo.class);
        System.out.println(objectMapper.writeValueAsString(demo));
        // System.out.println(Json.DEFAULT.write(demo));
        // assertThat(demo.dateTime).isEqualTo(LocalDate.parse("2020-05-05").atTime(LocalTime.MIN));
        // assertThat(demo.dateMinTime).isEqualTo(LocalDate.parse("2020-02-05").atTime(LocalTime.MIN));
        // assertThat(demo.dateMaxTime).isEqualTo(LocalDate.parse("2020-03-05").atTime(LocalTime.MAX));
    }

    @Data
    static class Demo {
        @DateTimeFormat(time = DateTimeFormat.Time.MIN)
        @JsonDeserialize(using = DateTimeFormatDeserializer.class)
        LocalDateTime dateTime;

        @DateMinTime
        @JsonDeserialize(using = DateTimeFormatDeserializer.class)
        LocalDateTime dateMinTime;

        @DateMaxTime
        @JsonDeserialize(using = DateTimeFormatDeserializer.class)
        LocalDateTime dateMaxTime;
    }
}