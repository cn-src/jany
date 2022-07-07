package cn.javaer.jany.ebean;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import io.ebean.DB;
import io.ebean.Database;
import io.ebean.SqlUpdate;
import io.ebean.Transaction;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author cn-src
 */
public class DbUtils {

    public static <E> int upsert(E bean, UpsertMode mode) {
        Assert.notNull(bean);
        return upsert(Collections.singletonList(bean), mode)[0];
    }

    public static <E> int upsert(Database db, E bean, UpsertMode mode) {
        Assert.notNull(bean);
        return upsert(db, Collections.singletonList(bean), mode)[0];
    }

    public static <E> int[] upsert(List<E> beans, UpsertMode mode) {
        return upsert(DB.getDefault(), beans, mode);
    }

    /**
     * 注意：此方法没有 Ebean 原生支持的一些特性。
     *
     * @param beans beans
     * @param mode mode
     * @param <E> e
     *
     * @return int
     */
    public static <E> int[] upsert(Database db, List<E> beans, UpsertMode mode) {
        Assert.notEmpty(beans);
        Assert.notNull(mode);

        Class<?> entityClass = beans.get(0).getClass();
        final Field[] persistFields = PersistUtils.getPersistFields(entityClass);
        Assert.notEmpty(persistFields);

        final StringJoiner columns = new StringJoiner(",", "(", ")");
        final StringJoiner values = new StringJoiner(",", "(", ")");
        final StringJoiner sets = new StringJoiner(",");
        String upsertColumnName = "";
        for (Field field : persistFields) {
            final String columnName = PersistUtils.columnName(field);
            if (!field.isAnnotationPresent(UpsertInsertTransient.class)) {
                columns.add(columnName);
                values.add(":" + columnName);
            }
            if (!field.isAnnotationPresent(UpsertUpdateTransient.class)) {
                sets.add(columnName + "= :" + columnName);
            }
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

        final SqlUpdate sqlUpdate = db.sqlUpdate(sb.toString());
        try (Transaction txn = db.beginTransaction()) {
            for (E bean : beans) {
                for (Field field : persistFields) {
                    final String columnName = PersistUtils.columnName(field);
                    sqlUpdate.setParameter(columnName, ReflectUtil.getFieldValue(bean, field));
                }
                sqlUpdate.addBatch();
            }
            int[] rows = sqlUpdate.executeBatch();
            txn.commit();
            return rows;
        }
    }
}