package cn.javaer.jany.spring.web.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class ErrorMessageSourceTest {

    @Test
    void getMessage() {
        final String message = ErrorMessageSource.getMessage("non_key");
        assertThat(message).isEmpty();
    }
}