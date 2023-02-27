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

package cn.javaer.jany.spring.autoconfigure.springdoc;

import cn.javaer.jany.exception.ErrorCode;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.spring.autoconfigure.web.exception.ExceptionAutoConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springdoc.core.Constants;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Collections;
import java.util.MissingFormatArgumentException;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author cn-src
 */
class SpringdocAutoConfigurationTest {

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
        .withConfiguration(
            AutoConfigurations.of(
                SpringdocAutoConfiguration.class,
                ExceptionAutoConfiguration.class,

                SpringDocConfiguration.class,
                SpringDocConfigProperties.class,

                SpringDocWebMvcConfiguration.class,
                MultipleOpenApiSupportConfiguration.class,

                SpringDocDataRestConfiguration.class,

                MockMvcAutoConfiguration.class,
                WebMvcAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class))
        .withPropertyValues("server.error.include-message=always");
//            "springdoc.api-docs.version=openapi_3_1");

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
        public Page<Demo> get(final Pageable pageable)
            throws Demo1Exception, Demo2Exception, MissingFormatArgumentException {

            return new PageImpl<>(Collections.singletonList(new Demo()), pageable, 1);
        }

        @GetMapping("test2")
        public cn.javaer.jany.model.Page<Demo> get(final PageParam pageParam) {
            return null;
        }
    }

    @Data
    static class Demo {
        @Schema(description = "a time")
        LocalTime localTime;
    }

    @ErrorCode(error = "DEMO1_ERROR", status = 400, doc = "测试错误1")
    static class Demo1Exception extends RuntimeException {

    }

    @ErrorCode(error = "DEMO2_ERROR", status = 400, doc = "测试错误2")
    static class Demo2Exception extends RuntimeException {

    }
}