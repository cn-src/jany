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

import cn.javaer.jany.exception.RuntimeErrorInfo;
import org.jetbrains.annotations.Nullable;

/**
 * 自定义异常信息提供器。
 *
 * @author cn-src
 */
public interface ErrorInfoProvider {

    /**
     * 根据异常，提供错误信息。
     *
     * @param t Throwable
     *
     * @return ErrorInfo
     */
    @Nullable
    RuntimeErrorInfo getRuntimeErrorInfo(Throwable t);
}