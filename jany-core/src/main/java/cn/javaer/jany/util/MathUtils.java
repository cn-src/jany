package cn.javaer.jany.util;

import cn.hutool.core.math.MathUtil;

/**
 * 用于进行数学运算的工具类。
 *
 * @author cn-src
 */
public class MathUtils extends MathUtil {
    public static int sum(int[] arr) {
        int sum = 0;
        for (int j : arr) {
            sum += j;
        }
        return sum;
    }
}