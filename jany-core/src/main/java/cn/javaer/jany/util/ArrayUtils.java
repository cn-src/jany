package cn.javaer.jany.util;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.ArrayUtil;

import java.util.Collection;

/**
 * @author cn-src
 */
public class ArrayUtils extends ArrayUtil {

    /**
     * 如果对象是一个数组，则返回它。如果是集合，则将其转换为数组。否则，抛出异常。
     *
     * @param obj 要转换为数组的对象。
     *
     * @return 对象数组。
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object[] toObjectArray(Object obj) {
        if (obj == null) {
            return new Object[0];
        }
        if (obj.getClass().isArray()) {
            return (Object[]) obj;
        }
        if (obj instanceof Collection) {
            final Class<?> type = IterUtil.getElementType((Collection) obj);
            if (type == null) {
                return new Object[0];
            }
            return ArrayUtil.toArray((Collection) obj, type);
        }
        throw new IllegalArgumentException("unsupported type: " + obj.getClass());
    }
}