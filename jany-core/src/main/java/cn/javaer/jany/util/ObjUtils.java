package cn.javaer.jany.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;

import java.util.Collection;

/**
 * @author cn-src
 */
public interface ObjUtils {

    static Collection<?> toCollection(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Collection) {
            return (Collection<?>) obj;
        }
        throw new IllegalArgumentException("unsupported type: " + obj.getClass());
    }

    @SuppressWarnings("rawtypes")
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