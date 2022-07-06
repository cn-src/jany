package cn.javaer.jany.spring.web;

import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.model.Sort;
import cn.javaer.jany.util.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author cn-src
 */
public class PageParamArgumentResolver implements HandlerMethodArgumentResolver {

    public static final PageParamArgumentResolver INSTANCE = new PageParamArgumentResolver();

    public static final SortArgumentResolver DEFAULT_SORT_RESOLVER = SortArgumentResolver.INSTANCE;

    private SortArgumentResolver sortResolver = DEFAULT_SORT_RESOLVER;

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
        int page = NumberUtils.parseInt(pageStr, PageParam.DEFAULT_PAGE);
        int size = NumberUtils.parseInt(sizeStr, PageParam.DEFAULT_SIZE);

        Sort sort = sortResolver.resolveArgument
            (methodParameter, mavContainer, webRequest, binderFactory);
        if (sort != null && sort.isSorted()) {
            return PageParam.of(page, size, sort);
        }
        return PageParam.of(page, size);
    }

    public void setSortResolver(SortArgumentResolver sortResolver) {
        this.sortResolver = sortResolver == null ? DEFAULT_SORT_RESOLVER : sortResolver;
    }
}