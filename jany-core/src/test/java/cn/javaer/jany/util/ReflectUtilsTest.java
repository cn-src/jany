package cn.javaer.jany.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class ReflectUtilsTest {

    @Test
    void getSuperclassGenerics() {
        final Class<?>[] classes = ReflectUtils.getSuperclassGenerics(
            new Demo<String, Long>() {}.getClass());
        assertThat(classes).hasSize(2);
        assertThat(classes[0]).isEqualTo(String.class);
        assertThat(classes[1]).isEqualTo(Long.class);
    }

    static class Demo<T, ID> {}
}