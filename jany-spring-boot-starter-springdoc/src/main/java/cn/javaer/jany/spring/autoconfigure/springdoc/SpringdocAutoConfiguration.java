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

import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.spring.autoconfigure.web.exception.ExceptionAutoConfiguration;
import cn.javaer.jany.spring.security.PrincipalId;
import cn.javaer.jany.spring.web.exception.ErrorInfoProcessor;
import io.swagger.v3.core.util.PrimitiveType;
import org.springdoc.core.*;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.converters.PageableOpenAPIConverter;
import org.springdoc.core.parsers.ReturnTypeParser;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.core.service.GenericResponseService;
import org.springdoc.core.service.OperationService;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.springdoc.core.utils.Constants.SPRINGDOC_PAGEABLE_CONVERTER_ENABLED;

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
@EnableConfigurationProperties(SpringdocProperties.class)
public class SpringdocAutoConfiguration implements InitializingBean {

    @Bean
    @ConditionalOnMissingBean
    GenericResponseService responseBuilder(final OperationService operationService,
                                           final ErrorInfoProcessor errorInfoProcessor,
                                           final List<ReturnTypeParser> returnTypeParsers,
                                           final SpringDocConfigProperties springDocConfigProperties,
                                           final PropertyResolverUtils propertyResolverUtils) {
        return new ExceptionResponseBuilder(operationService, returnTypeParsers,
                springDocConfigProperties, propertyResolverUtils, errorInfoProcessor);
    }

    @Override
    public void afterPropertiesSet() {
        SpringDocUtils.getConfig().replaceParameterObjectWithClass(
                PageParam.class, PageableDoc.class);
        PrimitiveType.customClasses().put("java.time.LocalTime", PrimitiveType.PARTIAL_TIME);
    }

    @ConditionalOnClass(Pageable.class)
    static class SpringDocPageableConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public SpringPageConverter springPageConverter(ObjectMapperProvider springDocObjectMapper) {
            return new SpringPageConverter(springDocObjectMapper);
        }

        // org.springdoc.core.configuration.SpringDocPageableConfiguration.pageableOpenAPIConverter
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
            SpringDocUtils.getConfig().addAnnotationsToIgnore(PrincipalId.class);
            return new PageableOpenAPIConverter(objectMapperProvider);
        }
    }
}