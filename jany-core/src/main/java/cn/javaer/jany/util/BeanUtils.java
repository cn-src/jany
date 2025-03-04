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

import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.util.ObjUtil;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author cn-src
 */
public class BeanUtils extends BeanUtil {

    /**
     * 将JavaBean转换为SortedMap
     * 此方法用于将给定的JavaBean对象转换为一个按照键自然排序的TreeMap
     * 如果指定忽略空值，则不会将空值包含在返回的Map中
     *
     * @param bean             要转换的JavaBean对象如果为null，则返回null
     * @param ignoreEmptyValue 是否忽略空值如果为true，则不包含空值；否则包含空值
     * @return 返回一个包含JavaBean属性的SortedMap如果输入为null，则返回null
     */
    public static SortedMap<String, Object> beanToSortedMap(Object bean, boolean ignoreEmptyValue) {
        // 检查输入的JavaBean对象是否为null
        if (null == bean) {
            return null;
        }

        // 使用BeanUtil工具类将JavaBean转换为Map
        final Map<String, Object> map = BeanUtil.beanToMap(bean);
        // 创建一个TreeMap来存储结果，以确保键值按照自然顺序排列
        final TreeMap<String, Object> result = new TreeMap<>();
        // 遍历Map中的每个键值对
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            // 如果设置了忽略空值，并且当前值为空，则跳过当前键值对
            if (ignoreEmptyValue && ObjUtil.isEmpty(entry.getValue())) {
                continue;
            }
            // 将键值对添加到结果Map中
            result.put(entry.getKey(), entry.getValue());
        }
        // 返回包含所有属性的SortedMap
        return result;
    }
}