package cn.javaer.jany.spring.format;

import cn.javaer.jany.format.DateMaxTime;
import cn.javaer.jany.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
public class DateMaxTimeTest {

    @Test
    void name() throws Exception {

        final Field dateTime = Demo.class.getDeclaredField("dateTime");
        final DateTimeFormat annotation = AnnotatedElementUtils.findMergedAnnotation(dateTime,
            DateTimeFormat.class);
        assertThat(Objects.requireNonNull(annotation).time())
            .isEqualTo(DateTimeFormat.Time.MAX);
    }

    static class Demo {
        @DateMaxTime
        LocalDateTime dateTime;
    }
}