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

import cn.dev33.satoken.exception.NotLoginException;
import cn.javaer.jany.exception.ErrorInfo;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class ErrorMessageSourceTest {

    @Test
    void getMessage_empty() {
        final String message = ErrorMessageSource.getMessage("non_key");
        assertThat(message).isEmpty();
    }

    @Test
    void getMessage() {
        final String message = ErrorMessageSource.getMessage("400");
        assertThat(message).isEqualTo("请求错误");
    }

    @Test
    void getMessage_SpEL() {

        final String message = ErrorMessageSource.getMessage(ErrorInfo.of401("$SA_TOKEN_NOT_LOGIN"),
                NotLoginException.newInstance("loginType", NotLoginException.BE_REPLACED, "message", "token"));
        assertThat(message).isEqualTo("您已在别处登录");
    }

    @Test
    void getMessage_SpEL2() {
        final String message = ErrorMessageSource.getMessage(ErrorInfo.of401(
                        "$SA_TOKEN_NOT_LOGIN"),
                NotLoginException.newInstance("loginType", "99", "message", "token"));
        assertThat(message).isEqualTo("认证失败，未知错误");
    }
}