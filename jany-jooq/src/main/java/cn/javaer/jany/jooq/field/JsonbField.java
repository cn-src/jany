package cn.javaer.jany.jooq.field;

import cn.javaer.jany.jackson.Json;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.CustomField;
import org.jooq.impl.DSL;

import java.util.Collections;

/**
 * @author cn-src
 */
public class JsonbField<R extends Record, T> extends CustomField<T> implements TableField<R, T> {
    private static final long serialVersionUID = -2128410511798819756L;
    private final Table<R> table;
    private final Field<?>[] arguments;

    public JsonbField(final String name, final DataType<T> type, final Field<?>... argument) {
        super(name, type);
        this.arguments = argument;
        this.table = null;
    }

    public JsonbField(final String name, final DataType<T> type, final Table<R> table) {
        super(name, type);
        this.arguments = null;
        this.table = table;
        CustomFieldUtils.addToFields(table, this);
    }

    @Override
    public void accept(final Context ctx) {
        if (null != this.arguments) {
            ctx.sql(this.getName());
            ctx.sql('(');
            int i = 1;
            for (final Field<?> arg : this.arguments) {
                ctx.visit(arg);
                if (i != this.arguments.length) {
                    ctx.sql(',');
                }
                i++;
            }
            ctx.sql(')');
        }
        else {
            if (ctx.qualify()) {
                ctx.visit(this.table);
                ctx.sql('.');
            }
            ctx.visit(this.getUnqualifiedName());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Support(SQLDialect.POSTGRES)
    public Condition jsonbContains(final T object) {
        if (object instanceof Field) {
            return this.jsonbContains((Field) object);
        }
        final String val;
        if (object instanceof String) {
            val = (String) object;
        }
        else if (object instanceof JSONB) {
            val = ((JSONB) object).data();
        }
        else {
            val = object == null ? null : Json.DEFAULT.write(object);
        }
        return DSL.condition("{0} @> {1}::jsonb", this,
            DSL.val(val, this.getDataType()));
    }

    @Support(SQLDialect.POSTGRES)
    public Condition jsonbContains(final Field<T> field) {
        return DSL.condition("{0} @> {1}", this, field);
    }

    @Support(SQLDialect.POSTGRES)
    public Condition jsonbContains(final String jsonKey, final Object jsonValue) {
        final String json = Json.DEFAULT.write(Collections.singletonMap(jsonKey, jsonValue));
        return DSL.condition("{0} @> {1}::jsonb", this, DSL.val(json, this.getDataType()));
    }

    @Override
    public Table<R> getTable() {
        return this.table;
    }
}