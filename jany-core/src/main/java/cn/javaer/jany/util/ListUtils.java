package cn.javaer.jany.util;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * @author cn-src
 */
public class ListUtils extends ListUtil {

    /**
     * 如果对象是集合、数组或可迭代对象，则返回其元素列表；否则，返回包含该对象的列表。
     *
     * @param obj 要转换为列表的对象。
     *
     * @return 对象列表。
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Collection) {
            return toList((Collection) obj);
        }
        if (obj.getClass().isArray()) {
            return toList((T[]) ArrayUtil.wrap(obj));
        }
        if (obj instanceof Iterable) {
            return toList((Iterable) obj);
        }
        return ListUtil.toList((T) obj);
    }
}