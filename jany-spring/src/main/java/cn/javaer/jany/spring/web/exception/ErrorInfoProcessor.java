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

package cn.javaer.jany.spring.web.exception;

import cn.hutool.core.util.ArrayUtil;
import cn.javaer.jany.exception.ErrorInfo;
import cn.javaer.jany.exception.RuntimeErrorInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cn-src
 */
public interface ErrorInfoProcessor {

    /**
     * 根据异常，提供错误信息。
     *
     * @param t Throwable
     *
     * @return ErrorInfo
     */
    @NotNull RuntimeErrorInfo getRuntimeErrorInfo(@NotNull Throwable t);

    /**
     * 根据异常，提供错误信息。
     *
     * @param clazz Throwable
     *
     * @return ErrorInfo
     */
    @NotNull ErrorInfo getErrorInfo(@NotNull Class<? extends Throwable> clazz);

    /**
     * 根据异常，获取运行时错误信息。
     *
     * @param e Throwable
     *
     * @return 错误信息
     */
    @Nullable String getTraceMessage(Throwable e);

    /**
     * 根据配置转换成 ErrorInfo
     *
     * @param mapping 配置
     *
     * @return ErrorInfo 配置
     */
    static Map<String, ErrorInfo> convert(Map<String, String> mapping) {

        if (!CollectionUtils.isEmpty(mapping)) {
            Map<String, ErrorInfo> errorInfoMap = new HashMap<>(mapping.size());
            for (final Map.Entry<String, String> entry : mapping.entrySet()) {
                final String value = entry.getValue();
                if (StringUtils.hasText(value)) {
                    final String[] split = StringUtils.split(value, ",");
                    if (ArrayUtil.length(split) != 2) {
                        // TODO
                        throw new RuntimeException(value);
                    }
                    final ErrorInfo errorInfo = ErrorInfo.of(split[1].trim(),
                        Integer.parseInt(split[0].trim()));
                    errorInfoMap.put(entry.getKey(), errorInfo);
                }
            }
            return Collections.unmodifiableMap(errorInfoMap);
        }
        return Collections.emptyMap();
    }
}