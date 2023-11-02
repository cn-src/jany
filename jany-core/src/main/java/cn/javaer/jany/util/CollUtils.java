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

package cn.javaer.jany.util;

import org.dromara.hutool.core.collection.CollUtil;

import java.util.Collection;

/**
 * @author cn-src
 */
public class CollUtils extends CollUtil {

    /**
     * 如果对象是一个集合，则返回它，否则抛出异常。
     *
     * @param obj 要转换为集合的对象。
     *
     * @return 对象的集合。
     */
    public static Collection<?> cast(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Collection) {
            return (Collection<?>) obj;
        }
        throw new IllegalArgumentException("unsupported type: " + obj.getClass());
    }
}