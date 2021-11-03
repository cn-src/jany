package cn.javaer.jany.spring.autoconfigure.springdoc;

import cn.javaer.jany.spring.exception.DefinedErrorInfo;
import cn.javaer.jany.spring.web.exception.ErrorInfoExtractor;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.GenericResponseService;
import org.springdoc.core.MethodAttributes;
import org.springdoc.core.OperationService;
import org.springdoc.core.PropertyResolverUtils;
import org.springdoc.core.ReturnTypeParser;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.TreeSet;

/**
 * @author cn-src
 */
class ExceptionResponseBuilder extends GenericResponseService {

    private final ErrorInfoExtractor errorInfoExtractor;

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
                                    final ErrorInfoExtractor errorInfoExtractor) {
        super(operationBuilder, returnTypeParsers, springDocConfigProperties,
            propertyResolverUtils);
        this.errorInfoExtractor = errorInfoExtractor;
    }

    @Override
    public ApiResponses build(final Components components, final HandlerMethod handlerMethod,
                              final Operation operation, final MethodAttributes methodAttributes) {
        final ApiResponses apiResponses = super.build(components, handlerMethod, operation,
            methodAttributes);
        final Class<?>[] exceptionTypes = handlerMethod.getMethod().getExceptionTypes();
        final TreeSet<DefinedErrorInfo> errorInfos = new TreeSet<>();
        for (final Class<?> exceptionType : exceptionTypes) {
            @SuppressWarnings("unchecked")
            final DefinedErrorInfo errorInfo = this.errorInfoExtractor.getErrorInfoWithMessage(
                (Class<? extends Throwable>) exceptionType);
            errorInfos.add(errorInfo);
        }
        for (final DefinedErrorInfo errorInfo : errorInfos) {
            final ApiResponse response = new ApiResponse();
            response.setDescription(String.format("%s: %s", errorInfo.getError(),
                errorInfo.getMessage()));
            apiResponses.addApiResponse(String.valueOf(errorInfo.getStatus()), response);
        }
        return apiResponses;
    }
}