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

import cn.hutool.core.util.StrUtil;
import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.exception.RuntimeErrorInfo;
import cn.javaer.jany.spring.web.exception.ErrorInfoProcessor;
import cn.javaer.jany.spring.web.exception.ErrorMessageSource;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.*;
import org.springdoc.core.models.MethodAttributes;
import org.springdoc.core.parsers.ReturnTypeParser;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.service.GenericResponseService;
import org.springdoc.core.service.OperationService;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.web.method.HandlerMethod;

import java.util.*;

/**
 * @author cn-src
 */
@Slf4j
class ExceptionResponseBuilder extends GenericResponseService {

    private final ErrorInfoProcessor errorInfoProcessor;

    /**
     * Instantiates a new Generic response builder.
     *
     * @param operationBuilder the operation builder
     * @param returnTypeParsers the return type parsers
     * @param springDocConfigProperties the spring doc config properties
     * @param propertyResolverUtils the property resolver utils
     * @param errorInfoProcessor ErrorInfoProcessor
     */
    public ExceptionResponseBuilder(final OperationService operationBuilder,
                                    final List<ReturnTypeParser> returnTypeParsers,
                                    final SpringDocConfigProperties springDocConfigProperties,
                                    final PropertyResolverUtils propertyResolverUtils,
                                    final ErrorInfoProcessor errorInfoProcessor) {
        super(operationBuilder, returnTypeParsers, springDocConfigProperties,
            propertyResolverUtils);
        this.errorInfoProcessor = errorInfoProcessor;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public ApiResponses build(final Components components, final HandlerMethod handlerMethod,
                              final Operation operation, final MethodAttributes methodAttributes) {
        final ApiResponses apiResponses = super.build(components, handlerMethod, operation,
            methodAttributes);
        final Class<?>[] exceptionTypes = handlerMethod.getMethod().getExceptionTypes();
        final Map<Integer, Set<ErrorInfo>> errorInfos = new LinkedHashMap<>();
        for (final Class<?> exceptionType : exceptionTypes) {
            @SuppressWarnings("unchecked")
            final ErrorInfo errorInfo = this.errorInfoProcessor.getErrorInfo(
                (Class<? extends Throwable>) exceptionType);
            if (errorInfos.containsKey(errorInfo.getStatus())) {
                errorInfos.get(errorInfo.getStatus()).add(errorInfo);
            }
            else {
                errorInfos.put(errorInfo.getStatus(), new TreeSet<>(List.of(errorInfo)));
            }
        }
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
            .readAllAsResolvedSchema(new AnnotatedType().type(RuntimeErrorInfo.class));
        for (Map.Entry<Integer, Set<ErrorInfo>> entry : errorInfos.entrySet()) {

            final ApiResponse response = new ApiResponse();
            response.setDescription(ErrorMessageSource.getMessage(entry.getKey(), "Unknown"));
            ObjectSchema schema = new ObjectSchema();
            final List<Schema<?>> errorSchemas = new ArrayList<>();
            for (ErrorInfo errorInfo : entry.getValue()) {
                StringSchema ss = new StringSchema();
                String desc = StrUtil.firstNonEmpty(errorInfo.getDoc(),
                    ErrorMessageSource.getMessage(errorInfo),
                    errorInfo.getMessage(),
                    "No description");
                ss.description("常量值：" + errorInfo.getError() + "；" + desc);
                errorSchemas.add(ss);
            }
            schema.required(resolvedSchema.schema.getRequired());
            schema.properties(new LinkedHashMap<>(resolvedSchema.schema.getProperties()));
            schema.addProperty(RuntimeErrorInfo.Fields.error,
                new Schema().oneOf(errorSchemas));
            response.setContent(new Content().addMediaType("application/json",
                new MediaType().schema(schema)));
            apiResponses.addApiResponse(String.valueOf(entry.getKey()), response);
        }
        return apiResponses;
    }
}