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