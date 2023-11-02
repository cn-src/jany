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

    String error;

    int status;

    String message;

    String doc;

    ErrorInfo(final String error, final int status) {
        Objects.requireNonNull(error);
        this.error = error;
        this.status = status;
        this.doc = "";
        this.message = "";
    }

    ErrorInfo(final String error, final int status, final String message, final String doc) {
        Objects.requireNonNull(error);
        this.error = error;
        this.status = status;
        this.message = message;
        this.doc = doc;
    }

    public static ErrorInfo of(final ErrorCode errorCode) {
        return new ErrorInfo(errorCode.error(), errorCode.status(), errorCode.message(), errorCode.doc());
    }

    public static ErrorInfo of(final String error, final int status) {
        return new ErrorInfo(error, status);
    }

    public static ErrorInfo of400(final String error) {
        return new ErrorInfo(error, 400);
    }

    public static ErrorInfo of401(final String error) {
        return new ErrorInfo(error, 401);
    }

    public static ErrorInfo of403(final String error) {
        return new ErrorInfo(error, 403);
    }

    public static ErrorInfo of500(final String error) {
        return new ErrorInfo(error, 500);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ErrorInfo that = (ErrorInfo) o;
        return Objects.equals(this.error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.error);
    }

    @Override
    public int compareTo(final @NotNull ErrorInfo errorInfo) {
        return Comparator.comparing(ErrorInfo::getStatus, Integer::compare)
                .thenComparing(ErrorInfo::getError, String::compareTo)
                .compare(this, errorInfo);
    }
}