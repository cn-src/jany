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
 *
 */

package cn.javaer.jany.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;

class AnnotationUtilsTest {

    @Test
    void testFindMergedAnnotation1() {
        // Setup
        final Annotation ann = Demo.class.getAnnotation(Ann1.class);

        // Run the test
        final MetaAnn result = AnnotationUtils.findMergedAnnotation(MetaAnn.class, ann).get();

        // Verify the results
        Assertions.assertThat(result.name()).isEqualTo("name1");
    }

    @Test
    void testFindMergedAnnotation2() {
        // Setup
        final Annotation annotations = Demo.class.getAnnotation(Ann1.class);

        // Run the test
        final MetaAnn result = AnnotationUtils.findMergedAnnotation(MetaAnn.class, annotations).get();

        // Verify the results
        Assertions.assertThat(result.name()).isEqualTo("name1");
    }

    @Test
    void testFindMergedAnnotation3() {
        // Setup
        final AnnotatedElement element = Demo.class;

        // Run the test
        final MetaAnn result = AnnotationUtils.findMergedAnnotation(MetaAnn.class, element).get();

        // Verify the results
        Assertions.assertThat(result.name()).isEqualTo("name1");
    }

    @Test
    void testGetAnnotationValueMap() {
        // Setup
        final Annotation annotation = Ann1.class.getAnnotation(MetaAnn.class);

        // Run the test
        final Map<String, Object> result = AnnotationUtils.getAnnotationValueMap(annotation);

        // Verify the results
        Assertions.assertThat(result).containsEntry("name", "name1");
    }

    @Ann1
    class Demo {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @MetaAnn(name = "name1")
    @interface Ann1 {
        String name() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @interface MetaAnn {
        String name() default "";
    }
}