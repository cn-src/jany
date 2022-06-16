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
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GenericResponseService;
import org.springdoc.core.MethodAttributes;
import org.springdoc.core.OperationService;
import org.springdoc.core.PropertyResolverUtils;
import org.springdoc.core.ReturnTypeParser;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
     * @param errorInfoExtractor errorInfoExtractor
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
                errorInfos.put(errorInfo.getStatus(), new TreeSet<>(Arrays.asList(errorInfo)));
            }
        }
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
            .readAllAsResolvedSchema(new AnnotatedType().type(RuntimeErrorInfo.class));
        for (Map.Entry<Integer, Set<ErrorInfo>> entry : errorInfos.entrySet()) {

            final ApiResponse response = new ApiResponse();
            response.setDescription(ErrorMessageSource.getMessage(entry.getKey(), "Unknown"));
            ObjectSchema schema = new ObjectSchema();
            final List<Schema> errorSchemas = new ArrayList<>();
            for (ErrorInfo errorInfo : entry.getValue()) {
                StringSchema ss = new StringSchema();
                String desc = StrUtil.firstNonEmpty(errorInfo.getDoc(),
                    ErrorMessageSource.getMessage(errorInfo),
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