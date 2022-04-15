package cn.javaer.jany.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;

import java.util.Collection;

/**
 * @author cn-src
 */
public interface ObjUtils {

    /**
     * 强转 String 类型。
     *
     * @param obj 对象
     *
     * @return String
     *
     * @throws IllegalArgumentException 如果对象不是 String 类型
     */
    static String castString(Object obj) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        throw new IllegalArgumentException("Object must be String, but was " + obj.getClass());
    }

    static Collection<?> toCollection(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Collection) {
            return (Collection<?>) obj;
        }
        throw new IllegalArgumentException("unsupported type: " + obj.getClass());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static Object[] toObjectArray(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass().isArray()) {
            return (Object[]) obj;
        }
        if (obj instanceof Collection) {
            final Class<?> type = CollUtil.getElementType((Collection) obj);
            if (type == null) {
                return new Object[0];
            }
            return ArrayUtil.toArray((Collection) obj, type);
        }
        throw new IllegalArgumentException("unsupported type: " + obj.getClass());
    }
}