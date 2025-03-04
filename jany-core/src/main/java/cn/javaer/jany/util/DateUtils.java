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

import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.math.NumberUtil;

/**
 * @author cn-src
 */
public class DateUtils extends DateUtil {

    /**
     * 将给定的纳秒数转换为更易读的时间格式
     * 此方法根据纳秒数的大小，将其转换为毫秒、秒、分钟、小时或天，并附加相应的单位
     *
     * @param nanos 待转换的纳秒数
     * @return 转换后的易读时间字符串
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public static String humanReadable(long nanos) {
        // 对于小于1秒的纳秒数，转换为毫秒
        if (nanos < 1000_000_000) {
            return nanos / 1000000 + "ms";
        }
        // 对于小于1分钟的纳秒数，转换为秒，并保留一位小数
        else if (nanos < 60_000_000_000L) {
            return NumberUtil.div((double) nanos, 1000000000D, 1) + "s";
        }
        // 对于小于1小时的纳秒数，转换为分钟，并保留一位小数
        else if (nanos < 3600_000_000_000L) {
            return NumberUtil.div((double) nanos, 60_000_000_000D, 1) + "m";
        }
        // 对于小于1天的纳秒数，转换为小时，并保留一位小数
        else if (nanos < 86400_000_000_000L) {
            return NumberUtil.div((double) nanos, 3600_000_000_000D, 1) + "h";
        }
        // 对于大于等于1天的纳秒数，转换为天，并保留一位小数
        else {
            return NumberUtil.div((double) nanos, 86400_000_000_000D, 1) + "d";
        }
    }
}