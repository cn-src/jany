package cn.javaer.jany.spring.web;

import cn.javaer.jany.model.PageParam;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author cn-src
 */
public class PageParamArgumentResolver implements HandlerMethodArgumentResolver {

    public static final PageParamArgumentResolver INSTANCE = new PageParamArgumentResolver();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PageParam.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter methodParameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        String pageStr = webRequest.getParameter("page");
        String sizeStr = webRequest.getParameter("size");
        int page = PageParam.DEFAULT_PAGE;
        int size = PageParam.DEFAULT_SIZE;
        try {
            if (StringUtils.hasText(pageStr)) {
                page = Integer.parseInt(pageStr);
            }
            if (StringUtils.hasText(sizeStr)) {
                size = Integer.parseInt(sizeStr);
            }
        }
        catch (NumberFormatException ignore) {

        }
        return PageParam.of(page, size);
    }
}