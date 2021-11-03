package cn.javaer.jany.spring.autoconfigure.web.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author cn-src
 */
class ExceptionAutoConfigurationTest {
    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(ExceptionAutoConfiguration.class,
            MockMvcAutoConfiguration.class, WebMvcAutoConfiguration.class,
            DispatcherServletAutoConfiguration.class,
            HttpMessageConvertersAutoConfiguration.class,
            PropertyPlaceholderAutoConfiguration.class))
        .withPropertyValues("jany.exception.mapping.demo=400,demo-value",
            "server.error.include-message=always");

    @Test
    void auto() {
        this.contextRunner.withUserConfiguration(Demo.class)
            .run(context -> {
                final MockMvc mockMvc = context.getBean(MockMvc.class);
                final Object result = mockMvc.perform(get("/demo"))
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

                final Object errorInfos = mockMvc.perform(get("/error_infos"))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

                System.out.println(result);
                System.out.println(errorInfos);
            });
    }

    @RestController
    static class Demo {
        @GetMapping("demo")
        void callError() throws MissingServletRequestParameterException, MyException {
            throw new MissingServletRequestParameterException("key", "value");
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "MY_BAD_REQUEST")
    static class MyException extends RuntimeException {

        private static final long serialVersionUID = 176179922741709936L;
    }
}