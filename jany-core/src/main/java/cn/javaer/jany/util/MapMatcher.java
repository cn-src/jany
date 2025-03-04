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

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * @author cn-src
 */
public class MapMatcher<K, R> {
    private final Map<K, Function<K, R>> mapping;

    public MapMatcher(Map<K, Function<K, R>> mapping) {
        this.mapping = mapping;
    }

    /**
     * 根据给定的键应用相应的函数
     * <p>
     * 此方法旨在通过提供的键找到映射中对应的函数，并执行该函数
     * 如果在映射中找到了对应的函数，则对该键执行该函数，并返回结果
     * 如果没有找到对应的函数，则返回null
     *
     * @param key 映射到函数的键，用于查找映射中对应的函数
     * @return 执行找到的函数后的结果，如果没有找到对应的函数则返回null
     */
    @Nullable
    public R applyBy(K key) {
        // 从映射中获取与键关联的函数
        Function<K, R> fn = mapping.get(key);
        if (fn != null) {
            // 如果找到了函数，则应用该函数并返回结果
            return fn.apply(key);
        }
        // 如果没有找到函数，则返回null
        return null;
    }

    /**
     * 根据给定的键应用对应的函数，或者如果找不到对应的函数，则抛出异常
     * 此方法用于处理特定键的函数映射，它尝试在预定义的映射中找到与键关联的函数，并执行该函数
     * 如果找不到对应的函数，则抛出NoSuchElementException异常，指示没有找到匹配的键
     *
     * @param key 要处理的键，用于查找对应的函数
     * @return null 此方法不返回任何值，执行函数或抛出异常后返回null
     * @throws NoSuchElementException 如果没有找到与键关联的函数时抛出此异常
     */
    public R applyOrThrowBy(K key) {
        // 尝试从映射中获取与键关联的函数
        Function<K, R> fn = mapping.get(key);
        // 如果找到了对应的函数，则应用该函数
        if (fn != null) {
            return fn.apply(key);
        }
        // 如果没有找到对应的函数，则抛出异常
        else {
            throw new NoSuchElementException("Not match, key: " + key);
        }
    }

    public static <K, R> MapMatcher<K, R> of(K k, Function<K, R> fn) {
        return new MapMatcher<>(Map.of(k, fn));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
                k6, fn6));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6,
                                             K k7, Function<K, R> fn7) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
                k6, fn6, k7, fn7));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6,
                                             K k7, Function<K, R> fn7,
                                             K k8, Function<K, R> fn8) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
                k6, fn6, k7, fn7, k8, fn8));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6,
                                             K k7, Function<K, R> fn7,
                                             K k8, Function<K, R> fn8,
                                             K k9, Function<K, R> fn9) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
                k6, fn6, k7, fn7, k8, fn8, k9, fn9));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6,
                                             K k7, Function<K, R> fn7,
                                             K k8, Function<K, R> fn8,
                                             K k9, Function<K, R> fn9,
                                             K k10, Function<K, R> fn10) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
                k6, fn6, k7, fn7, k8, fn8, k9, fn9, k10, fn10));
    }
}