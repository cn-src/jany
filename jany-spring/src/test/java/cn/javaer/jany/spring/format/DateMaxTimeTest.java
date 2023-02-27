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