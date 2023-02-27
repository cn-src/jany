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

package cn.javaer.jany.ebean.expression;

import cn.javaer.jany.util.AnnotationUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class WhereExpressionTest {
    @Test
    void name() throws Exception {
        final Field name = Demo.class.getField("name");
        final WhereExpression whereExpression = AnnotationUtils.findMergedAnnotation(WhereExpression.class, name).get();
        assertThat(whereExpression.property()).isEqualTo("name");
    }

    static class Demo {
        @WhereInRangeStart(property = "name")
        public String name;
    }
}