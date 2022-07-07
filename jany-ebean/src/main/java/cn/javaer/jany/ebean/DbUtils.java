package cn.javaer.jany.ebean;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import io.ebean.DB;
import io.ebean.SqlUpdate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author cn-src
 */
public class DbUtils {

    public static <E> int[] upsert(List<E> beans, UpsertMode mode) {
        Assert.notEmpty(beans);
        Assert.notNull(mode);

        Class<?> entityClass = beans.get(0).getClass();
        final Field[] persistFields = PersistUtils.getPersistFields(entityClass);
        Assert.notEmpty(persistFields);

        final StringJoiner columns = new StringJoiner(",", "(", ")");
        final StringJoiner values = new StringJoiner(",", "(", ")");
        final StringJoiner sets = new StringJoiner(",");
//        final List<Map<String, Object>> fieldValues = List(persistFields.length);
        String upsertColumnName = "";
        for (Field field : persistFields) {
            final String columnName = PersistUtils.columnName(field);
            columns.add(columnName);
            values.add(":").add(columnName);
            sets.add(columnName).add("= :").add(columnName);
            if (field.isAnnotationPresent(UpsertKey.class)) {
                upsertColumnName = columnName;
            }
        }

        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb = sb.append(PersistUtils.tableName(entityClass))
            .append(' ').append(columns)
            .append(" VALUES ").append(values)
            .append("ON CONFLICT (").append(upsertColumnName);

        switch (mode) {
            case UPDATE:
                sb.append(") DO UPDATE SET ").append(sets);
                break;
            case NOTHING:
                sb.append(") DO NOTHING");
                break;
            default:
                throw new IllegalArgumentException("UpsertMode error");
        }

        final SqlUpdate sqlUpdate = DB.sqlUpdate(sb.toString());
        for (E bean : beans) {
            for (Field field : persistFields) {
                final String columnName = PersistUtils.columnName(field);
                sqlUpdate.setParameter(columnName, ReflectUtil.getFieldValue(bean, field));
            }
            sqlUpdate.addBatch();
        }
        return sqlUpdate.executeBatch();
    }
}