package cn.javaer.jany.ebean;

import cn.hutool.core.lang.Assert;
import io.ebean.DB;

import java.lang.reflect.Field;
import java.util.StringJoiner;

/**
 * @author cn-src
 */
public class DbUtils {

    public static int upsert(Object bean, UpsertMode mode) {

        final Field[] persistFields = PersistUtils.getPersistFields(bean.getClass());
        Assert.notEmpty(persistFields);

        final StringJoiner columns = new StringJoiner(",", "(", ")");
        final StringJoiner values = new StringJoiner(",", "(", ")");
        final StringJoiner sets = new StringJoiner(",");
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
        sb = sb.append(PersistUtils.tableName(bean.getClass()))
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

        return DB.sqlUpdate(sb.toString()).execute();
    }
}