/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NumberUtilsTest {

    @Test
    void parseInt() {
        assertThat(NumberUtils.parseInt((Object) null, 2)).isEqualTo(2);
        assertThat(NumberUtils.parseInt((Object) "1", 2)).isEqualTo(1);
        assertThat(NumberUtils.parseInt((Object) "xxx", 2)).isEqualTo(2);
        assertThat(NumberUtils.parseInt(new Object(), 2)).isEqualTo(2);

        assertThat(NumberUtils.parseInt(1.0, 2)).isEqualTo(1);
        assertThat(NumberUtils.parseInt(1, 2)).isEqualTo(1);
        assertThat(NumberUtils.parseInt(3L, 2)).isEqualTo(3);
    }
}