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

import cn.hutool.core.io.resource.ResourceUtil;
import cn.javaer.jany.jackson.Json;

import java.util.List;

/**
 * @author cn-src
 */
public interface ResourceUtils {

    /**
     * Read list.
     *
     * @param <T> the type parameter
     * @param resource the resource
     * @param clazz the clazz
     *
     * @return the list
     */
    static <T> List<T> readJson(String resource, Class<T> clazz) {
        return Json.DEFAULT.readList(ResourceUtil.readUtf8Str(resource), clazz);
    }
}