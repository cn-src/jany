package cn.javaer.jany.util;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MapMatcherTest {
    final MapMatcher<String, String> matcher = MapMatcher.of(
        "k1", it -> "v1",
        "k2", it -> "v2",
        "k3", it -> "v3");

    @Test
    void applyBy() {
        assertThat(matcher.applyBy("k2")).isEqualTo("v2");
    }

    @Test
    void applyOrThrowBy() {
        assertThatThrownBy(() -> matcher.applyBy("not fount"))
            .isInstanceOf(NoSuchElementException.class);
    }
}