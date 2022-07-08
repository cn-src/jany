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
        @Override
        public void addFormatters(final FormatterRegistry registry) {
            registry.addFormatterForFieldAnnotation(new DateTimeFormatter());
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(PageParamArgumentResolver.INSTANCE);
            resolvers.add(SortArgumentResolver.INSTANCE);
        }
    }
}