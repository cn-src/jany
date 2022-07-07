package cn.javaer.jany.ebean;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.core.util.ReflectUtil;
import cn.javaer.jany.util.ClassUtils;
import cn.javaer.jany.util.StrUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;

/**
 * @author cn-src
 */
public class PersistUtils {
    private static final WeakConcurrentMap<Class<?>, Field[]> FIELDS_CACHE =
        new WeakConcurrentMap<>();

    public static Field[] getPersistFields(Class<?> entityClass) {
        Assert.notNull(entityClass);
        return FIELDS_CACHE.computeIfAbsent(entityClass, () -> ReflectUtil.getFields(entityClass,
            field -> !field.getName().startsWith("_ebean")
                && ClassUtils.isNotStatic(field)
                && ClassUtils.isNotTransient(field)
                && !field.isAnnotationPresent(Transient.class)));
    }

    public static String tableName(Class<?> entityClass) {
        return Opt.ofNullable(entityClass.getAnnotation(Table.class))
            .map(Table::name)
            .filter(StrUtils::isNotEmpty)
            .orElse(StrUtils.toSnakeLower(entityClass.getSimpleName()));
    }

    public static String columnName(Field field) {
        return Opt.ofNullable(field.getAnnotation(Column.class))
            .map(Column::name)
            .filter(StrUtils::isNotEmpty)
            .orElse(StrUtils.toSnakeLower(field.getName()));
    }
}