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

import org.dromara.hutool.core.math.MathUtil;

/**
 * 用于进行数学运算的工具类。
 *
 * @author cn-src
 */
public class MathUtils extends MathUtil {
    /**
     * 计算整数数组中所有元素的总和
     *
     * @param arr 整数数组，包含需要相加的元素
     * @return 数组中所有元素的总和
     */
    public static int sum(int[] arr) {
        // 初始化总和变量为0
        int sum = 0;
        // 遍历数组中的每个元素，将其累加到总和变量中
        for (int j : arr) {
            sum += j;
        }
        // 返回计算得到的总和
        return sum;
    }
}