package cn.javaer.jany.ebean;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import io.ebean.DB;
import io.ebean.Database;
import io.ebean.SqlUpdate;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author cn-src
 */
public class DbUtils {

    public static int insert(Map<String, Object> row, String tableName) {
        Assert.notNull(row);
        return insert(Collections.singletonList(row), tableName, row.keySet())[0];
    }

    public static int insert(Map<String, Object> row, String tableName, Set<String> columns) {
        return insert(Collections.singletonList(row), tableName, columns)[0];
    }

    public static int[] insert(List<Map<String, Object>> rowList, String tableName) {
        Assert.notEmpty(rowList);

        return insert(rowList, tableName, rowList.get(0).keySet());
    }

    public static int[]
    insert(List<Map<String, Object>> rowList, String tableName, Set<String> columns) {
        Assert.notEmpty(rowList);
        Assert.notEmpty(columns);
        Assert.notEmpty(tableName);

        final SqlUpdate sql = DB.sqlUpdate(new StringBuilder()
            .append("INSERT INTO ")
            .append(tableName)
            .append(" (")
            .append(String.join(",", columns))
            .append(") VALUES (")
            .append(columns.stream().map(col -> ":" + col).collect(Collectors.joining(",")))
            .append(")")
            .toString());
        for (Map<String, Object> row : rowList) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                sql.setParameter(entry.getKey(), entry.getValue());
            }
            sql.addBatch();
        }

        return sql.executeBatch();
    }

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
        sb.append(PersistUtils.tableName(entityClass))
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