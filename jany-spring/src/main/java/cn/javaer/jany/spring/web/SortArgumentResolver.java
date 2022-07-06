package cn.javaer.jany.spring.web;

import cn.javaer.jany.model.Sort;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author cn-src
 */
public class SortArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DEFAULT_PARAMETER = "sort";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Sort.class.equals(parameter.getParameterType());
    }

    @Override
    public Sort resolveArgument(MethodParameter parameter,
                                @Nullable ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                @Nullable WebDataBinderFactory binderFactory) {

        String[] directionParameter = webRequest.getParameterValues(DEFAULT_PARAMETER);

        // No parameter
        if (directionParameter == null) {
            return Sort.DEFAULT;
        }

        // Single empty parameter, e.g "sort="
        if (directionParameter.length == 1 && !StringUtils.hasText(directionParameter[0])) {
            return Sort.DEFAULT;
        }

        return Sort.parse(directionParameter);
    }
}