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

    private SortArgumentResolver sortResolver = SortArgumentResolver.INSTANCE;

    private final int defaultMaxSize;

    public PageParamArgumentResolver(int defaultMaxSize) {
        this.defaultMaxSize = defaultMaxSize;
    }

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
        int size = Math.min(defaultMaxSize, NumberUtils.parseInt(sizeStr, PageParam.DEFAULT_SIZE));

        Sort sort = sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        if (sort != null && sort.isSorted()) {
            return PageParam.of(page, size, sort);
        }
        return PageParam.of(page, size);
    }

    public void setSortResolver(SortArgumentResolver sortResolver) {
        this.sortResolver = sortResolver == null ? SortArgumentResolver.INSTANCE : sortResolver;
    }
}