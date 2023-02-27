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

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cn-src
 */
class DateUtilsTest {

    @Test
    void humanReadable() {
        assertEquals("1ms", DateUtils.humanReadable(1000000L));

        assertEquals("1.0s", DateUtils.humanReadable(1000000L * 1000));
        assertEquals("1.3s", DateUtils.humanReadable(1000000L * 1300));
        assertEquals("1.5s", DateUtils.humanReadable(1000000L * 1500));
        assertEquals("1.8s", DateUtils.humanReadable(1000000L * 1800));

        assertEquals("1.0m", DateUtils.humanReadable(1000000L * 1000 * 60));
        assertEquals("1.5m", DateUtils.humanReadable(1000000L * 1000 * 90));
        assertEquals("2.0m", DateUtils.humanReadable(1000000L * 1000 * 60 * 2));

        assertEquals("1.0h", DateUtils.humanReadable(1000000L * 1000 * 60 * 60));
        assertEquals("1.5h", DateUtils.humanReadable(1000000L * 1000 * 60 * 90));
        assertEquals("2.0h", DateUtils.humanReadable(1000000L * 1000 * 60 * 60 * 2));

        assertEquals("1.0d", DateUtils.humanReadable(1000000L * 1000 * 60 * 60 * 24));
        assertEquals("1.5d", DateUtils.humanReadable(1000000L * 1000 * 60 * 60 * 36));
        assertEquals("2.0d", DateUtils.humanReadable(1000000L * 1000 * 60 * 60 * 24 * 2));
    }
}