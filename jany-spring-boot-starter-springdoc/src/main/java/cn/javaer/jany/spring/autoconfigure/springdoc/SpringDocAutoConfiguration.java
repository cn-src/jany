package cn.javaer.jany.spring.autoconfigure.springdoc;

import cn.javaer.jany.spring.autoconfigure.web.exception.ExceptionAutoConfiguration;
import cn.javaer.jany.spring.web.exception.ErrorInfoExtractor;
import org.springdoc.core.GenericResponseService;
import org.springdoc.core.OperationService;
import org.springdoc.core.PropertyResolverUtils;
import org.springdoc.core.ReturnTypeParser;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * SpringDoc 支持.
 *
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({SpringDocConfiguration.class})
@AutoConfigureAfter(value = {ExceptionAutoConfiguration.class})
@AutoConfigureBefore({SpringDocConfiguration.class, SpringDocConfigProperties.class})
@ConditionalOnProperty(prefix = "jany.springdoc", name = "enabled", havingValue = "true",
    matchIfMissing = true)
public class SpringDocAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    GenericResponseService responseBuilder(final OperationService operationBuilder,
                                           final ErrorInfoExtractor errorInfoExtractor,
                                           final List<ReturnTypeParser> returnTypeParsers,
                                           final SpringDocConfigProperties springDocConfigProperties,
                                           final PropertyResolverUtils propertyResolverUtils) {
        return new ExceptionResponseBuilder(operationBuilder, returnTypeParsers,
            springDocConfigProperties, propertyResolverUtils, errorInfoExtractor);
    }
}