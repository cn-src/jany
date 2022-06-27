package cn.javaer.jany.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;

class AnnotationUtilsTest {

    @Test
    void testFindMergedAnnotation1() {
        // Setup
        final Annotation ann = Demo.class.getAnnotation(Ann1.class);

        // Run the test
        final MetaAnn result = AnnotationUtils.findMergedAnnotation(MetaAnn.class, ann);

        // Verify the results
        Assertions.assertThat(result.name()).isEqualTo("name1");
    }

    @Test
    void testFindMergedAnnotation2() {
        // Setup
        final Annotation annotations = Demo.class.getAnnotation(Ann1.class);

        // Run the test
        final MetaAnn result = AnnotationUtils.findMergedAnnotation(MetaAnn.class, annotations);

        // Verify the results
        Assertions.assertThat(result.name()).isEqualTo("name1");
    }

    @Test
    void testFindMergedAnnotation3() {
        // Setup
        final AnnotatedElement element = Demo.class;

        // Run the test
        final MetaAnn result = AnnotationUtils.findMergedAnnotation(element, MetaAnn.class);

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