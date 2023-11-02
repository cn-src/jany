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

import cn.javaer.jany.jackson.Json;
import lombok.SneakyThrows;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.io.resource.ResourceUtil;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;

/**
 * @author cn-src
 */
public interface JsonAssert {

    /**
     * 断言 json, 非严格模式.
     *
     * @param jsonFile json file
     * @param actualJson json
     */
    @SneakyThrows
    static void assertEquals(File jsonFile, String actualJson) {
        JSONAssert.assertEquals(FileUtil.readUtf8String(jsonFile), actualJson,
            false);
    }

    /**
     * 断言 json, 非严格模式.
     *
     * @param jsonFile json file
     * @param actual object to json string
     */
    @SneakyThrows
    static void assertEquals(File jsonFile, Object actual) {
        JSONAssert.assertEquals(FileUtil.readUtf8String(jsonFile), Json.DEFAULT.write(actual),
            false);
    }

    /**
     * 断言 json, 非严格模式.
     *
     * @param resource json file resource
     * @param actual object to json string
     */
    @SneakyThrows
    static void assertEquals(String resource, Object actual) {
        JSONAssert.assertEquals(ResourceUtil.readUtf8Str(resource),
            Json.DEFAULT.write(actual), false);
    }

    /**
     * 断言 json, 非严格模式，但需要保证元素顺序.
     *
     * @param jsonFile json file
     * @param actual object to json string
     */
    @SneakyThrows
    static void assertEqualsAndOrder(File jsonFile, Object actual) {
        JSONAssert.assertEquals(FileUtil.readUtf8String(jsonFile), Json.DEFAULT.write(actual),
            JSONCompareMode.STRICT_ORDER);
    }

    /**
     * 断言 json, 非严格模式，但需要保证元素顺序.
     *
     * @param resource json file resource
     * @param actual object to json string
     */
    @SneakyThrows
    static void assertEqualsAndOrder(String resource, Object actual) {
        JSONAssert.assertEquals(ResourceUtil.readUtf8Str(resource), Json.DEFAULT.write(actual),
            JSONCompareMode.STRICT_ORDER);
    }

    /**
     * 断言 json, 非严格模式，但需要保证元素顺序.
     *
     * @param resource json file resource
     * @param actualJson object to json string
     */
    @SneakyThrows
    static void assertEqualsAndOrder(String resource, String actualJson) {
        JSONAssert.assertEquals(ResourceUtil.readUtf8Str(resource), actualJson,
            JSONCompareMode.STRICT_ORDER);
    }
}