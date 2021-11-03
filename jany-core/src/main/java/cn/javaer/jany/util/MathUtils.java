package cn.javaer.jany.util;

/**
 * @author cn-src
 */
public interface MathUtils {

    /**
     * 包装类型取最小值，null 为最大.
     *
     * @param a the a
     * @param b the b
     *
     * @return the long
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