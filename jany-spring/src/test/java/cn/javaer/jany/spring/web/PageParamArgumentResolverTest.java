package cn.javaer.jany.spring.web;

import cn.javaer.jany.model.PageParam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author cn-src
 */
class PageParamArgumentResolverTest {

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(
            MockMvcAutoConfiguration.class, WebMvcAutoConfiguration.class,
            DispatcherServletAutoConfiguration.class,
            HttpMessageConvertersAutoConfiguration.class,
            PropertyPlaceholderAutoConfiguration.class));

    @Test
    void resolveArgument() {
        contextRunner.withUserConfiguration(Config.class, ArgumentConfig.class)
            .run(context -> {
                final MockMvc mockMvc = context.getBean(MockMvc.class);
                mockMvc.perform(get("/demo")).andExpect(status().isOk()).andReturn();
            });
    }

    static class Config {
        @Bean
        DemoController demoController() {
            return new DemoController();
        }
    }

    @Configuration
    static class ArgumentConfig implements WebMvcConfigurer {

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(PageParamArgumentResolver.INSTANCE);
        }
    }

    @RestController
    static class DemoController {
        @GetMapping("demo")
        String demo(PageParam pageParam) {
            assertThat(pageParam.getPage()).isEqualTo(1);
            assertThat(pageParam.getSize()).isEqualTo(20);
            return "ok";
        }
    }
}