package cn.javaer.jany.spring.autoconfigure.web.exception;

import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.spring.web.exception.ErrorInfoController;
import cn.javaer.jany.spring.web.exception.ErrorInfoExtractor;
import cn.javaer.jany.spring.web.exception.GlobalErrorAttributes;
import cn.javaer.jany.spring.web.exception.GlobalExceptionAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;

import java.util.Map;

/**
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ExceptionMappingProperties.class,
    ServerProperties.class, ErrorProperties.class})
@ConditionalOnWebApplication
@AutoConfigureBefore({ErrorMvcAutoConfiguration.class})
@ConditionalOnProperty(prefix = "jany.web.exception", name = "enabled", havingValue = "true",
    matchIfMissing = true)
public class ExceptionAutoConfiguration implements InitializingBean {
    private final ExceptionMappingProperties exceptionMappingProperties;
    private Map<String, ErrorInfo> useMapping;

    public ExceptionAutoConfiguration(
        final ExceptionMappingProperties exceptionMappingProperties) {
        this.exceptionMappingProperties = exceptionMappingProperties;
    }

    @Bean
    ErrorInfoController errorInfoController() {
        return new ErrorInfoController();
    }

    @Bean
    ErrorInfoExtractor errorInfoExtractor() {
        return new ErrorInfoExtractor(this.useMapping);
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    GlobalExceptionAdvice globalExceptionAdvice(ErrorProperties errorProperties,
                                                final ErrorInfoExtractor errorInfoExtractor) {
        return new GlobalExceptionAdvice(errorProperties, errorInfoExtractor);
    }

    @Bean
    @ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
    GlobalErrorAttributes globalErrorAttributes(final ErrorInfoExtractor errorInfoExtractor) {
        return new GlobalErrorAttributes(errorInfoExtractor);
    }

    @Bean(name = "error")
    @ConditionalOnMissingBean(name = "error")
    View errorView() {
        return new ErrorView();
    }

    @Override
    public void afterPropertiesSet() {
        this.useMapping = ErrorInfoExtractor.convert(exceptionMappingProperties.getMapping());
    }
}