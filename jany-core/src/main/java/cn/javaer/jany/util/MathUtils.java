package cn.javaer.jany.util;

/**
 * 用于进行数学运算的工具类。
 *
 * @author cn-src
 */
public interface MathUtils {

    /**
     * 返回 a 和 b 的最小值，null 值最大。
     *
     * @param a 要比较的第一个数字。
     * @param b 要与 a 进行比较的值。
     *
     * @return 两个值中的最小值。
     */
    static Long min(Long a, Long b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return (a <= b) ? a : b;
    }
}