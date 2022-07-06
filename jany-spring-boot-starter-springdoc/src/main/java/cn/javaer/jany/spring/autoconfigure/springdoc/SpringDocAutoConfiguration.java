package cn.javaer.jany.spring.autoconfigure.springdoc;

import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.spring.autoconfigure.web.exception.ExceptionAutoConfiguration;
import cn.javaer.jany.spring.security.PrincipalId;
import cn.javaer.jany.spring.web.exception.ErrorInfoProcessor;
import org.springdoc.core.GenericResponseService;
import org.springdoc.core.OperationService;
import org.springdoc.core.PropertyResolverUtils;
import org.springdoc.core.ReturnTypeParser;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.SpringDocUtils;
import org.springdoc.core.converters.PageableOpenAPIConverter;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.springdoc.core.Constants.SPRINGDOC_PAGEABLE_CONVERTER_ENABLED;

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
public class SpringDocAutoConfiguration implements InitializingBean {

    @Bean
    @ConditionalOnMissingBean
    GenericResponseService responseBuilder(final OperationService operationBuilder,
                                           final ErrorInfoProcessor errorInfoProcessor,
                                           final List<ReturnTypeParser> returnTypeParsers,
                                           final SpringDocConfigProperties springDocConfigProperties,
                                           final PropertyResolverUtils propertyResolverUtils) {
        return new ExceptionResponseBuilder(operationBuilder, returnTypeParsers,
            springDocConfigProperties, propertyResolverUtils, errorInfoProcessor);
    }

    @Override
    public void afterPropertiesSet() {
        SpringDocUtils.getConfig().replaceParameterObjectWithClass(
            PageParam.class, PageableDoc.class);
    }

    @ConditionalOnClass(Pageable.class)
    static class SpringDocPageableConfiguration {

        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnProperty(name = SPRINGDOC_PAGEABLE_CONVERTER_ENABLED, matchIfMissing = true)
        @Lazy(false)
        @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
        PageableOpenAPIConverter pageableOpenAPIConverter(ObjectMapperProvider objectMapperProvider) {
            SpringDocUtils.getConfig().replaceParameterObjectWithClass(
                org.springframework.data.domain.Pageable.class, PageableDoc.class);
            SpringDocUtils.getConfig().replaceParameterObjectWithClass(
                org.springframework.data.domain.PageRequest.class, PageableDoc.class);
            SpringDocUtils.getConfig().replaceWithClass(Page.class, PageDoc.class);
            SpringDocUtils.getConfig().addAnnotationsToIgnore(PrincipalId.class);
            return new PageableOpenAPIConverter(objectMapperProvider);
        }
    }
}