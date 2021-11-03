package cn.javaer.jany.jooq.condition;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.impl.CustomCondition;

import static org.jooq.impl.DSL.val;

/**
 * @author cn-src
 */
public final class ContainedInCondition<T> extends CustomCondition {

    private static final long serialVersionUID = -643041960796102638L;
    private final Field<T> lhs;
    private final Field<T> rhs;
    private final T value;

    public ContainedInCondition(final Field<T> field, final T value) {
        this.lhs = field;
        this.rhs = null;
        this.value = value;
    }

    public ContainedInCondition(final Field<T> field, final Field<T> rhs) {
        this.lhs = field;
        this.rhs = rhs;
        this.value = null;
    }

    @Override
    public void accept(final Context<?> ctx) {
        ctx.visit(this.lhs).sql(" <@ ").visit(this.rhs());
    }

    private Field<T> rhs() {
        return (this.rhs == null) ? val(this.value, this.lhs) : this.rhs;
    }
}