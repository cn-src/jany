package cn.javaer.jany.spring.autoconfigure.web.exception;

import cn.hutool.core.util.ArrayUtil;
import cn.javaer.jany.spring.exception.DefinedErrorInfo;
import cn.javaer.jany.spring.web.exception.ErrorInfoController;
import cn.javaer.jany.spring.web.exception.ErrorInfoExtractor;
import cn.javaer.jany.spring.web.exception.GlobalErrorAttributes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ExceptionMappingProperties.class, ServerProperties.class})
@ConditionalOnWebApplication
@AutoConfigureBefore({ErrorMvcAutoConfiguration.class})
@ConditionalOnProperty(prefix = "jany.web.exception", name = "enabled", havingValue = "true",
    matchIfMissing = true)
public class ExceptionAutoConfiguration implements InitializingBean {
    private final ExceptionMappingProperties exceptionMappingProperties;
    private Map<String, DefinedErrorInfo> useMapping;

    public ExceptionAutoConfiguration(
        final ExceptionMappingProperties exceptionMappingProperties,
        final ServerProperties serverProperties) {
        this.exceptionMappingProperties = exceptionMappingProperties;
    }

    @Bean
    @Lazy
    ErrorInfoController errorInfoController(final ErrorInfoExtractor errorInfoExtractor) {
        return new ErrorInfoController(errorInfoExtractor);
    }

    @Bean
    ErrorInfoExtractor errorInfoExtractor() {
        return new ErrorInfoExtractor(this.useMapping);
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
        final Map<String, String> mapping = this.exceptionMappingProperties.getMapping();

        if (!CollectionUtils.isEmpty(mapping)) {
            this.useMapping = new HashMap<>(mapping.size());
            for (final Map.Entry<String, String> entry : mapping.entrySet()) {
                final String value = entry.getValue();
                if (StringUtils.hasText(value)) {
                    final String[] split = StringUtils.split(value, ",");
                    if (ArrayUtil.length(split) < 2) {
                        continue;
                    }
                    final DefinedErrorInfo errorInfo = DefinedErrorInfo.of(split[1].trim(),
                        Integer.parseInt(split[0].trim()));
                    this.useMapping.put(entry.getKey(), errorInfo);
                }
            }
        }
    }
}