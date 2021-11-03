package cn.javaer.jany.spring.format;

import cn.javaer.jany.format.DateMaxTime;
import cn.javaer.jany.format.DateMinTime;
import cn.javaer.jany.format.DateTimeFormat;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author cn-src
 */
class DateTimeFormatterTest {
    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
        .withConfiguration(
            AutoConfigurations.of(MockMvcAutoConfiguration.class, WebMvcAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class,
                PropertyPlaceholderAutoConfiguration.class));

    @Test
    void fillTime() {
        this.contextRunner.withUserConfiguration(TestController.class, WebConfig.class)
            .run(context -> {
                final MockMvc mockMvc = context.getBean(MockMvc.class);
                mockMvc.perform(get("/test1?dateTime=2020-01-01")).andExpect(status().isOk());

                mockMvc.perform(post("/test2")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("dateTime", "2020-01-01")
                    .param("dateMaxTime", "2020-02-01")
                    .param("dateMinTime", "2020-02-01")
                )
                    .andExpect(status().isOk());
            });
    }

    @Configuration
    static class WebConfig implements WebMvcConfigurer {
        @Override
        public void addFormatters(final FormatterRegistry registry) {
            registry.addFormatterForFieldAnnotation(new DateTimeFormatter());
        }
    }

    @RestController
    @RequestMapping
    static class TestController {

        @GetMapping("test1")
        public String get(@DateTimeFormat(time = DateTimeFormat.Time.MAX)
                          @RequestParam final LocalDateTime dateTime) {
            Assertions.assertThat(dateTime).isEqualTo(LocalDate.parse("2020-01-01").atTime(LocalTime.MAX));
            return dateTime.toString();
        }

        @PostMapping("test2")
        public void post(final Demo demo) {
            Assertions.assertThat(demo.dateTime).isEqualTo(LocalDate.parse("2020-01-01").atTime(LocalTime.MIN));
            Assertions.assertThat(demo.dateMaxTime).isEqualTo(LocalDate.parse("2020-02-01").atTime(LocalTime.MAX));
            Assertions.assertThat(demo.dateMinTime).isEqualTo(LocalDate.parse("2020-02-01").atTime(LocalTime.MIN));
        }
    }

    @Data
    static class Demo {
        @DateTimeFormat(time = DateTimeFormat.Time.MIN)
        LocalDateTime dateTime;

        @DateMaxTime
        LocalDateTime dateMaxTime;

        @DateMinTime
        LocalDateTime dateMinTime;
    }
}