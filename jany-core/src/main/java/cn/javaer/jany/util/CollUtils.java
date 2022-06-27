package cn.javaer.jany.util;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;

/**
 * @author cn-src
 */
public class CollUtils extends CollUtil {

    /**
     * 如果对象是一个集合，则返回它，否则抛出异常。
     *
     * @param obj 要转换为集合的对象。
     *
     * @return 对象的集合。
     */
    public static Collection<?> cast(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Collection) {
            return (Collection<?>) obj;
        }
        throw new IllegalArgumentException("unsupported type: " + obj.getClass());
    }
}