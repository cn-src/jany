package cn.javaer.jany.util;

import cn.hutool.core.date.DateUtil;

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
            return nanos / 1000000000 + "s";
        }
        else if (nanos < 3600_000_000_000L) {
            return nanos / 60_000_000_000L + "m";
        }
        else if (nanos < 86400_000_000_000L) {
            return nanos / 3600_000_000_000L + "h";
        }
        else {
            return nanos / 86400_000_000_000L + "d";
        }
    }
}