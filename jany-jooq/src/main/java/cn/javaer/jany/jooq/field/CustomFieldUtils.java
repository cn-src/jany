package cn.javaer.jany.jooq.field;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.TableImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author cn-src
 */
interface CustomFieldUtils {
    /**
     * 添加字段到 jOOQ 默认字段表中.
     *
     * @param table table
     * @param field field
     */
    static void addToFields(final Table<?> table, final Field<?> field) {
        if (table instanceof TableImpl) {
            try {
                final Method fields0 = TableImpl.class.getDeclaredMethod("fields0");
                fields0.setAccessible(true);
                final Object fields = fields0.invoke(table);
                final Method add = fields.getClass().getDeclaredMethod("add", Field.class);
                add.setAccessible(true);
                add.invoke(fields, field);
            }
            catch (final IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                // TODO
                throw new RuntimeException(e);
            }
        }
    }
}