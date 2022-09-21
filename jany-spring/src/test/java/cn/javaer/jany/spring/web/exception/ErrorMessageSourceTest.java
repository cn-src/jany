package cn.javaer.jany.spring.web.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class ErrorMessageSourceTest {

    @Test
    void emptyMessage() {
        final String message = ErrorMessageSource.getMessage("non_key");
        assertThat(message).isEmpty();
    }

    @Test
    void getMessage() {
        final String message = ErrorMessageSource.getMessage("400");
        assertThat(message).isEqualTo("请求错误");
    }

    @Test
    void getMessage2() {

//        final String message = ErrorMessageSource.getMessage("LOGIN_ERROR",
//            NotLoginException.newInstance("loginType", NotLoginException.NOT_TOKEN, "token"));
//        System.out.println(message);
    }
}