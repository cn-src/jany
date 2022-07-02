package cn.javaer.jany.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * @author cn-src
 */
public class ObjectUtils extends ObjectUtil {

    /**
     * 如果源对象与所有对象都相等，则返回 true
     *
     * @param src 源对象。
     * @param tars 要比较的目标对象。
     *
     * @return boolean。
     */
    public static boolean allEquals(Object src, Object... tars) {
        if (src == null) {
            return false;
        }
        if (ArrayUtil.isEmpty(tars)) {
            return false;
        }
        for (Object tar : tars) {
            if (!src.equals(tar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 如果源对象不与所有对象都相等，则返回 true
     *
     * @param src 源对象。
     * @param tars 要比较的目标对象。
     *
     * @return boolean。
     */
    public static boolean notAllEquals(Object src, Object... tars) {
        return !allEquals(src, tars);
    }

    /**
     * 如果源对象等于任何目标对象，则返回 true
     *
     * @param src 源对象。
     * @param tars 要比较的目标对象。
     *
     * @return boolean。
     */
    public static boolean anyEquals(Object src, Object... tars) {
        if (src == null) {
            return false;
        }
        if (ArrayUtil.isEmpty(tars)) {
            return false;
        }
        for (Object tar : tars) {
            if (src.equals(tar)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果源对象不与任何目标对象相等，则返回 true
     *
     * @param src 源对象。
     * @param tars 要比较的目标对象。
     *
     * @return boolean。
     */
    public static boolean notAnyEquals(Object src, Object... tars) {
        return !anyEquals(src, tars);
    }
}