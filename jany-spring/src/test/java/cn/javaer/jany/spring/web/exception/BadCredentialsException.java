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

import cn.javaer.jany.exception.ErrorCode;
import lombok.Getter;

/**
 * @author cn-src
 */
@Getter
@ErrorCode(error = "$LOGIN_ERROR_BAD_CREDENTIALS", status = 401, doc = "用户名或密码错误")
class BadCredentialsException extends RuntimeException {
    private final long validTimes;

    public BadCredentialsException(long validTimes) {
        this.validTimes = validTimes;
    }
}