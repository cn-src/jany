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

    public static final SortArgumentResolver INSTANCE = new SortArgumentResolver();

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