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

package cn.javaer.jany.test;

import org.assertj.core.util.Arrays;
import org.dromara.hutool.core.map.MapUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author cn-src
 */
class JsonAssertTest {

    @Test
    void assertEquals_ok() {
        JsonAssert.assertEquals("classpath:/test.json",
            MapUtil.builder().put("k1", "v1").put("k2", Arrays.array("v2_1", "v2_2")).build());
    }

    @Test
    void assertEquals_ok_ignore_order() {
        JsonAssert.assertEquals("classpath:/test2.json",
            MapUtil.builder().put("k1", "v1").put("k2", Arrays.array("v2_1", "v2_2")).build());
    }

    @Test
    void assertEquals_fail() {
        Assertions.assertThrows(AssertionError.class,
            () -> JsonAssert.assertEquals("classpath:/test.json",
                MapUtil.builder().put("k1", "vv").build()));
    }

    @Test
    void assertEqualsOrder_ok() {
        JsonAssert.assertEqualsAndOrder("classpath:/test.json",
            MapUtil.builder().put("k1", "v1").put("k2", Arrays.array("v2_1", "v2_2")).build());
    }

    @Test
    void assertEqualsOrder_fail() {
        Assertions.assertThrows(AssertionError.class,
            () -> JsonAssert.assertEqualsAndOrder("classpath:/test.json",
                MapUtil.builder().put("k1", "vv").build()));
    }

    @Test
    void assertEqualsOrder_fail_order() {
        Assertions.assertThrows(AssertionError.class, () -> {
            JsonAssert.assertEqualsAndOrder("classpath:/test2.json",
                MapUtil.builder().put("k1", "v1").put("k2", Arrays.array("v2_1", "v2_2")).build());
        });
    }
}