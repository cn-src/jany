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
        assertThatThrownBy(() -> matcher.applyOrThrowBy("not fount"))
            .isInstanceOf(NoSuchElementException.class);
    }
}