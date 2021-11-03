package cn.javaer.jany.spring.autoconfigure.springdoc;

import cn.javaer.jany.spring.autoconfigure.web.exception.ExceptionAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springdoc.core.Constants;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.SpringDocHints;
import org.springdoc.data.rest.SpringDocDataRestConfiguration;
import org.springdoc.webmvc.core.MultipleOpenApiSupportConfiguration;
import org.springdoc.webmvc.core.SpringDocWebMvcConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author cn-src
 */
class SpringDocAutoConfigurationTest {

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
        .withConfiguration(
            AutoConfigurations.of(
                SpringDocPlusConfiguration.class,
                SpringDocAutoConfiguration.class,
                ExceptionAutoConfiguration.class,

                SpringDocConfiguration.class,
                SpringDocConfigProperties.class,
                SpringDocHints.class,

                SpringDocWebMvcConfiguration.class,
                MultipleOpenApiSupportConfiguration.class,

                SpringDocDataRestConfiguration.class,

                MockMvcAutoConfiguration.class,
                WebMvcAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class))
        .withPropertyValues("server.error.include-message=always");

    @Test
    void generateDoc() {
        this.contextRunner.withUserConfiguration(DemoController.class)
            .run(context -> {
                final MockMvc mockMvc = context.getBean(MockMvc.class);

                final MvcResult mvcResult =
                    mockMvc.perform(get(Constants.DEFAULT_API_DOCS_URL)).andExpect(status().isOk())
                        .andExpect(jsonPath("$.paths.['/test'].get.parameters[?(@.name=='page')]" +
                            ".schema.description", contains("分页-页码"))).andReturn();
                final String content = mvcResult.getResponse().getContentAsString
                    (StandardCharsets.UTF_8);
                System.out.println(content);
            });
    }

    @RestController
    @RequestMapping
    static class DemoController {

        @GetMapping("test")
        public Page<String> get(final Pageable pageable) throws MissingServletRequestParameterException {
            return new PageImpl<>(Collections.singletonList("page data"), pageable, 1);
        }
    }
}