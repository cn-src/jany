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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author cn-src
 */
public class BeanUtils extends BeanUtil {

    public static SortedMap<String, Object> beanToSortedMap(Object bean, boolean ignoreEmptyValue) {
        if (null == bean) {
            return null;
        }

        final Map<String, Object> map = BeanUtil.beanToMap(bean);
        final TreeMap<String, Object> result = new TreeMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (ignoreEmptyValue && ObjectUtil.isEmpty(entry.getValue())) {
                continue;
            }
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}