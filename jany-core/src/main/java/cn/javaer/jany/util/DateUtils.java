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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;

/**
 * @author cn-src
 */
public class DateUtils extends DateUtil {

    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public static String humanReadable(long nanos) {
        if (nanos < 1000_000_000) {
            return nanos / 1000000 + "ms";
        }
        else if (nanos < 60_000_000_000L) {
            return NumberUtil.div((double) nanos, 1000000000D, 1) + "s";
        }
        else if (nanos < 3600_000_000_000L) {
            return NumberUtil.div((double) nanos, 60_000_000_000D, 1) + "m";
        }
        else if (nanos < 86400_000_000_000L) {
            return NumberUtil.div((double) nanos, 3600_000_000_000D, 1) + "h";
        }
        else {
            return NumberUtil.div((double) nanos, 86400_000_000_000D, 1) + "d";
        }
    }
}