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

package cn.javaer.jany.spring.autoconfigure.web;

import cn.javaer.jany.spring.format.DateTimeFormatter;
import cn.javaer.jany.spring.web.PageParamArgumentResolver;
import cn.javaer.jany.spring.web.SortArgumentResolver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
@ConditionalOnProperty(prefix = "jany.web", name = "enabled", havingValue = "true",
    matchIfMissing = true)
@EnableConfigurationProperties(WebMvcProperties.class)
public class WebAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public static class JanyWebMvcConfigurer implements WebMvcConfigurer {
        private final WebMvcProperties webMvcProperties;

        public JanyWebMvcConfigurer(WebMvcProperties webMvcProperties) {
            this.webMvcProperties = webMvcProperties;
        }

        @Override
        public void addFormatters(final FormatterRegistry registry) {
            registry.addFormatterForFieldAnnotation(new DateTimeFormatter());
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(new PageParamArgumentResolver(webMvcProperties.getPageParam().getDefaultMaxSize()));
            resolvers.add(SortArgumentResolver.INSTANCE);
        }
    }
}