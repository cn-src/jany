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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author cn-src
 */
class ObjectUtilsTest {

    @Test
    void testAllEquals() {
        Assertions.assertEquals(true, ObjectUtils.allEquals("n1", "n1", "n1"));
        Assertions.assertEquals(false, ObjectUtils.allEquals("n1", "n1", "n2"));
    }

    @Test
    void testAllNotEquals() {
        Assertions.assertEquals(true, ObjectUtils.notAllEquals("n1", "n2", "n3"));
        Assertions.assertEquals(false, ObjectUtils.notAllEquals("n1", "n1", "n1"));
    }

    @Test
    void testAnyEquals() {
        Assertions.assertEquals(true, ObjectUtils.anyEquals("n1", "n1", "n2"));
        Assertions.assertEquals(false, ObjectUtils.anyEquals("n1", "n2", "n3"));
    }

    @Test
    void testAnyNotEquals() {
        Assertions.assertEquals(true, ObjectUtils.notAnyEquals("n1", "n2", "n3"));
        Assertions.assertEquals(false, ObjectUtils.notAnyEquals("n1", "n1", "n2"));
    }
}