package cn.javaer.jany.jooq.field;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class ArrayFieldTest {
    final DSLContext dsl = DSL.using(SQLDialect.POSTGRES);

    @Test
    void arrayContains() {
        final Table<Record> demo = DSL.table("demo");
        final ArrayField<Record, String[]> arr1 = new ArrayField<>("arr1",
            SQLDataType.VARCHAR.getArrayDataType(), demo);
        final SelectConditionStep<Record> step =
            this.dsl.selectFrom(demo).where(arr1.containedIn(new String[]{"str1"}));

        assertThat(step.getSQL())
            .isEqualTo("select * from demo where demo.\"arr1\" <@ ?::varchar[]");
        
        assertThat(this.dsl.renderInlined(step))
            .isEqualTo("select * from demo where demo.\"arr1\" <@ cast('{\"str1\"}' as varchar[])");
    }
}