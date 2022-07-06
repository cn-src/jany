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