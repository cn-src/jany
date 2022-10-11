package cn.javaer.jany.jackson;

import cn.javaer.jany.format.DateMaxTime;
import cn.javaer.jany.format.DateMinTime;
import cn.javaer.jany.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class JanyJacksonAnnotationIntrospectorTest {
    @Test
    void jacksonIntrospector() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JanyJacksonAnnotationIntrospector jacksonIntrospector = new JanyJacksonAnnotationIntrospector();
        objectMapper.setAnnotationIntrospector(jacksonIntrospector);
        // language=JSON
        final Demo demo = objectMapper.readValue("{\"dateTime\": \"2020-05-05\",\"dateMinTime\": " +
            "\"2020-02-05\",\"dateMaxTime\": \"2020-03-05\"}", Demo.class);
        assertThat(demo.dateTime)
            .isEqualTo(LocalDate.parse("2020-05-05").atTime(LocalTime.MIN));
        assertThat(demo.dateMinTime)
            .isEqualTo(LocalDate.parse("2020-02-05").atTime(LocalTime.MIN));
        assertThat(demo.dateMaxTime)
            .isEqualTo(LocalDate.parse("2020-03-05").atTime(LocalTime.MAX));
    }

    @Test
    void jacksonIntrospectorValue() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(JanyJacksonAnnotationIntrospector.INSTANCE);
        // language=JSON
        final Demo1 demo = objectMapper.readValue("{\"dateTime\": \"2020-05-05\"}", Demo1.class);
        assertThat(demo.dateTime).isEqualTo(LocalDate.parse("2020-05-05").atTime(LocalTime.MIN));
    }

    @Data
    static class Demo {
        @DateTimeFormat(time = DateTimeFormat.Time.MIN)
        LocalDateTime dateTime;

        @DateMinTime
        LocalDateTime dateMinTime;

        @DateMaxTime
        LocalDateTime dateMaxTime;
    }

    @Value
    static class Demo1 {
        LocalDateTime dateTime;

        @JsonCreator
        @ConstructorProperties("dateTime")
        public Demo1(@DateTimeFormat(time = DateTimeFormat.Time.MIN) final LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }
    }
}