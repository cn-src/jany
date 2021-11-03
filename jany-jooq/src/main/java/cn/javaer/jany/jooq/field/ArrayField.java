package cn.javaer.jany.jooq.field;

import cn.javaer.jany.jooq.condition.ContainedInCondition;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.CustomField;

/**
 * @author cn-src
 */
public class ArrayField<R extends Record, T> extends CustomField<T> implements TableField<R, T> {
    private static final long serialVersionUID = -2128410511798819756L;
    private final Table<R> table;

    public ArrayField(final String name, final DataType<T> type, final Table<R> table) {
        super(name, type);
        this.table = table;
        CustomFieldUtils.addToFields(table, this);
    }

    @Override
    public void accept(final Context ctx) {
        if (ctx.qualify()) {
            ctx.visit(this.table);
            ctx.sql('.');
        }
        ctx.visit(this.getUnqualifiedName());
    }

    @Support(SQLDialect.POSTGRES)
    public Condition containedIn(final Field<T> field) {
        return new ContainedInCondition<>(this, field);
    }

    @Support(SQLDialect.POSTGRES)
    public Condition containedIn(final T values) {
        return new ContainedInCondition<>(this, values);
    }

    @Override
    public Table<R> getTable() {
        return this.table;
    }
}