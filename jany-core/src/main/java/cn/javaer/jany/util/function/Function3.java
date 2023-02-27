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

package cn.javaer.jany.util.function;

/**
 * @author cn-src
 */
@FunctionalInterface
public interface Function3<T1, T2, T3, R> {

    /**
     * 有返回值，有3个参数的函数.
     *
     * @param t1 the t1
     * @param t2 the t2
     * @param t3 the t3
     *
     * @return the r
     */
    R apply(T1 t1, T2 t2, T3 t3);
}