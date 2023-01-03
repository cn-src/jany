package cn.javaer.jany.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class TimeUtilsTest {

    @Test
    void monthStart() {
        final LocalDateTime dateTime = TimeUtils.monthStart();
        assertThat(dateTime.getDayOfMonth()).isEqualTo(1);
        assertThat(dateTime.toLocalTime()).isEqualTo(LocalTime.MIN);
    }

    @Test
    void monthEnd() {
        final LocalDateTime dateTime = TimeUtils.monthEnd();
        assertThat(dateTime.toLocalTime()).isEqualTo(LocalTime.MAX);
    }
}