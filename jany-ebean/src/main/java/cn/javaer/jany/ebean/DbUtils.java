package cn.javaer.jany.ebean;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import cn.javaer.jany.util.MathUtils;
import io.ebean.DB;
import io.ebean.Database;
import io.ebean.SqlUpdate;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 注意：此类中的方法没有 Ebean 原生支持的一些特性。
 *
 * @author cn-src
 */
public class DbUtils {

    public static int insert(InsertArgs insertArgs) {
        Assert.notNull(insertArgs.db());
        Assert.notEmpty(insertArgs.rowList());
        Assert.notEmpty(insertArgs.columns());
        Assert.notEmpty(insertArgs.tableName());

        final SqlUpdate sql = insertArgs.db().sqlUpdate(new StringBuilder()
            .append("INSERT INTO ")
            .append(insertArgs.tableName())
            .append(" (")
            .append(String.join(",", insertArgs.columns()))
            .append(") VALUES (")
            .append(insertArgs.columns().stream().map(col -> ":" + col).collect(Collectors.joining(",")))
            .append(")")
            .toString());
        return setParameters(sql, insertArgs.rowList());
    }

    public static int update(UpdateArgs updateArgs) {
        Assert.notNull(updateArgs.db());
        Assert.notEmpty(updateArgs.rowList());
        Assert.notEmpty(updateArgs.columns());
        Assert.notEmpty(updateArgs.tableName());
        Assert.notEmpty(updateArgs.updateKey());

        final SqlUpdate sql = updateArgs.db().sqlUpdate(new StringBuilder()
            .append("UPDATE ")
            .append(updateArgs.tableName())
            .append(" SET ")
            .append(updateArgs.columns().stream().map(col -> col + "=:" + col).collect(Collectors.joining(",")))
            .append(" WHERE ").append(updateArgs.updateKey()).append(" = :").append(updateArgs.updateKey())
            .toString());
        return setParameters(sql, updateArgs.rowList());
    }

    public static <E> int upsert(E bean, UpsertMode mode) {
        Assert.notNull(bean);
        return upsert(Collections.singletonList(bean), mode);
    }

    public static <E> int upsertDoUpdate(E bean) {
        Assert.notNull(bean);
        return upsert(Collections.singletonList(bean), UpsertMode.UPDATE);
    }

    public static <E> int upsertDoNothing(E bean) {
        Assert.notNull(bean);
        return upsert(Collections.singletonList(bean), UpsertMode.NOTHING);
    }

    public static <E> int upsert(Database db, E bean, UpsertMode mode) {
        Assert.notNull(bean);
        return upsert(db, Collections.singletonList(bean), mode);
    }

    public static <E> int upsertDoUpdate(Database db, E bean) {
        Assert.notNull(bean);
        return upsert(db, Collections.singletonList(bean), UpsertMode.UPDATE);
    }

    public static <E> int upsertDoNothing(Database db, E bean) {
        Assert.notNull(bean);
        return upsert(db, Collections.singletonList(bean), UpsertMode.NOTHING);
    }

    public static <E> int upsert(List<E> beans, UpsertMode mode) {
        return upsert(DB.getDefault(), beans, mode);
    }

    public static <E> int upsertDoUpdate(List<E> beans) {
        return upsert(DB.getDefault(), beans, UpsertMode.UPDATE);
    }

    public static <E> int upsertDoNothing(List<E> beans) {
        return upsert(DB.getDefault(), beans, UpsertMode.NOTHING);
    }

    public static <E> int upsertDoUpdate(Database db, List<E> beans) {
        return upsert(db, beans, UpsertMode.UPDATE);
    }

    public static <E> int upsertDoNothing(Database db, List<E> beans) {
        return upsert(db, beans, UpsertMode.NOTHING);
    }

    public static <E> int upsert(Database db, List<E> beans, UpsertMode mode) {
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
        return MathUtils.sum(sqlUpdate.executeBatch());
    }

    public static int upsert(UpsertArgs upsertArgs) {
        Assert.notNull(upsertArgs.db());
        Assert.notEmpty(upsertArgs.rowList());
        Assert.notEmpty(upsertArgs.tableName());
        Assert.notEmpty(upsertArgs.insertColumns());
        Assert.notEmpty(upsertArgs.updateColumns());
        Assert.notNull(upsertArgs.mode());

        final StringBuilder sqlBuilder = new StringBuilder()
            .append("INSERT INTO ")
            .append(upsertArgs.tableName())
            .append(" (")
            .append(String.join(",", upsertArgs.insertColumns()))
            .append(") VALUES (")
            .append(upsertArgs.insertColumns().stream().map(col -> ":" + col).collect(Collectors.joining(",")))
            .append(") ON CONFLICT (").append(upsertArgs.upsertKey());
        switch (upsertArgs.mode()) {
            case UPDATE:
                sqlBuilder.append(") DO UPDATE SET ").
                    append(upsertArgs.updateColumns().stream().map(col -> col + "= :" + col).collect(Collectors.joining(",")));
                break;
            case NOTHING:
                sqlBuilder.append(") DO NOTHING");
                break;
            default:
                throw new IllegalArgumentException("UpsertMode error");
        }
        final SqlUpdate sql = upsertArgs.db().sqlUpdate(sqlBuilder.toString());

        return setParameters(sql, upsertArgs.rowList());
    }

    private static int setParameters(SqlUpdate sql, List<Map<String, Object>> rowList) {
        for (Map<String, Object> row : rowList) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                sql.setParameter(entry.getKey(), entry.getValue());
            }
            sql.addBatch();
        }
        return MathUtils.sum(sql.executeBatch());
    }
}