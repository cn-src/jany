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

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author cn-src
 */
public class NumberUtils extends NumberUtil {

    /**
     * 如果字符串为空，则返回默认值。否则，尝试将字符串解析为整数。如果失败，返回默认值。
     *
     * @param intStr 要转换为整数的字符串。
     * @param defaultValue 默认值。
     *
     * @return 该方法返回字符串的整数值。
     */
    public static int parseInt(String intStr, int defaultValue) {
        if (StrUtil.isBlank(intStr)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(intStr);
        }
        catch (NumberFormatException ignore) {
            return defaultValue;
        }
    }
}