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

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.ArrayUtil;

import java.util.Collection;

/**
 * @author cn-src
 */
public class ArrayUtils extends ArrayUtil {

    /**
     * 如果对象是一个数组，则返回它。如果是集合，则将其转换为数组。否则，抛出异常。
     *
     * @param obj 要转换为数组的对象。
     *
     * @return 对象数组。
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object[] toObjectArray(Object obj) {
        if (obj == null) {
            return new Object[0];
        }
        if (obj.getClass().isArray()) {
            return (Object[]) obj;
        }
        if (obj instanceof Collection) {
            final Class<?> type = IterUtil.getElementType((Collection) obj);
            if (type == null) {
                return new Object[0];
            }
            return ArrayUtil.toArray((Collection) obj, type);
        }
        throw new IllegalArgumentException("unsupported type: " + obj.getClass());
    }
}