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