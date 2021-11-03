package cn.javaer.jany.jooq.field;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class JsonbFieldTest {

    @Test
    void contains() {
        final Condition condition = new JsonbField<>("ca", SQLDataType.JSONB, DSL.table("demo"))
            .jsonbContains(JSONB.valueOf("{}"));

        final DSLContext dsl = DSL.using(SQLDialect.POSTGRES);
        final SelectConditionStep<Record> step = dsl.selectFrom(DSL.table("demo"))
            .where(condition);
        final String renderInlined = dsl.renderInlined(step);
        assertThat(renderInlined)
            .isEqualTo("select * from demo where (demo.\"ca\" @> cast('{}' as jsonb)" +
                "::jsonb)");
    }

    @Test
    void containsKv() {
        final Condition condition = new JsonbField<>("ca", SQLDataType.JSONB, DSL.table("demo"))
            .jsonbContains("key", "value");

        final DSLContext dsl = DSL.using(SQLDialect.POSTGRES);
        final SelectConditionStep<Record> step = dsl.selectFrom(DSL.table("demo"))
            .where(condition);
        final String renderInlined = dsl.renderInlined(step);
        assertThat(renderInlined)
            .isEqualTo("select * from demo where (demo.\"ca\" @> cast" +
                "('{\"key\":\"value\"}' as jsonb)::jsonb)");
    }
}