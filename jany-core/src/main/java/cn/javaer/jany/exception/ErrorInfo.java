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

package cn.javaer.jany.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

/**
 * 错误信息。
 *
 * @author cn-src
 */
@Value
public class ErrorInfo implements Comparable<ErrorInfo> {

    // ---- 40x

    public static final String BAD_REQUEST = "BAD_REQUEST";

    public static final String UNAUTHORIZED = "UNAUTHORIZED";

    public static final String FORBIDDEN = "FORBIDDEN";

    public static final String NOT_FOUND = "NOT_FOUND";

    // ---- 50x

    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    /**
     * 登录错误。
     */
    public static final String LOGIN_ERROR = "LOGIN_ERROR";

    /**
     * 登录错误，用户名或密码错误。
     */
    public static final String LOGIN_ERROR_BAD_CREDENTIALS = "LOGIN_ERROR_BAD_CREDENTIALS";

    /**
     * 登录错误，账户被禁用。
     */
    public static final String LOGIN_ERROR_DISABLED = "LOGIN_ERROR_DISABLED";

    /**
     * Token 已过期。
     */
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";

    /**
     * Token 无效。
     */
    public static final String TOKEN_INVALID = "TOKEN_INVALID";

    /**
     * 被顶替下线。
     */
    public static final String SESSION_OUT_REPLACED = "SESSION_OUT_REPLACED";

    /**
     * 被强制下线。
     */
    public static final String SESSION_OUT_KICKED = "SESSION_OUT_KICKED";

    @EqualsAndHashCode.Include
    String error;

    int status;

    String message;

    String doc;

    private ErrorInfo(final String error, final int status) {
        Objects.requireNonNull(error, "Error code cannot be null");
        this.error = error;
        this.status = status;
        this.doc = null;
        this.message = null;
    }

    private ErrorInfo(final String error, final int status, final String message, final String doc) {
        Objects.requireNonNull(error, "Error code cannot be null");
        this.error = error;
        this.status = status;
        this.message = message;
        this.doc = doc;
    }

    public static ErrorInfo of(final ErrorCode errorCode) {
        if (errorCode == null) {
            throw new IllegalArgumentException("ErrorCode cannot be null");
        }
        return new ErrorInfo(errorCode.error(), errorCode.status(), errorCode.message(), errorCode.doc());
    }

    public static ErrorInfo of(final String error, final int status) {
        return new ErrorInfo(error, status);
    }

    @Override
    public int compareTo(@NotNull final ErrorInfo errorInfo) {
        return Comparator.comparingInt(ErrorInfo::getStatus)
                .thenComparing(ErrorInfo::getError)
                .compare(this, errorInfo);
    }
}