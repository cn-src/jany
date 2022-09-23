package cn.javaer.jany.spring.web.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.javaer.jany.exception.ErrorInfo;
import org.junit.jupiter.api.Test;

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
            NotLoginException.newInstance("loginType", NotLoginException.BE_REPLACED, "token"));
        assertThat(message).isEqualTo("您已在别处登录");
    }

    @Test
    void getMessage_SpEL2() {

        final String message = ErrorMessageSource.getMessage(ErrorInfo.of401(
            "$LOGIN_ERROR_BAD_CREDENTIALS"),
            new BadCredentialsException(3));
        System.out.println(message);
//        assertThat(message).isEqualTo("您已在别处登录");
    }
}