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

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * @author cn-src
 */
public class ListUtils extends ListUtil {

    /**
     * 如果对象是集合、数组或可迭代对象，则返回其元素列表；否则，返回包含该对象的列表。
     *
     * @param obj 要转换为列表的对象。
     *
     * @return 对象列表。
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Collection) {
            return ListUtil.toList((Collection) obj);
        }
        if (obj.getClass().isArray()) {
            return ListUtil.toList((T[]) ArrayUtil.wrap(obj));
        }
        if (obj instanceof Iterable) {
            return ListUtil.toList((Iterable) obj);
        }
        return ListUtil.toList((T) obj);
    }
}