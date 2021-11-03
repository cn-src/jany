package cn.javaer.jany.jooq.condition;

import cn.javaer.jany.jooq.PGDSL;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author cn-src
 */
class ConditionBuilderTest {

    @Test
    void append() {
        final Field<String> nullField = null;
        final Field<String> objectField = DSL.field("object", String.class);
        final Field<String[]> arrayField = DSL.field("array", String[].class);
        final Field<JSONB> jsonbField = DSL.field("jsonb", JSONB.class);
        final Field<LocalDateTime> dateTimeField = DSL.field("dateTime", LocalDateTime.class);

        final Condition condition = new ConditionBuilder()
            .optional(objectField::contains, "object")
            .optional(arrayField::contains, new String[]{"str1", "str2"})
            .optional(dateTimeField::betweenSymmetric, LocalDateTime.now(), LocalDateTime.now())
            .optional(PGDSL::containedIn, arrayField, new String[]{"value"})
            .optional(PGDSL::jsonbContains, jsonbField, "key", "value")
            .optional(Field::eq, nullField, "")
            .build();

        final String sql = DSL.using(SQLDialect.POSTGRES)
            .select(objectField, arrayField, jsonbField)
            .from(DSL.table("demo_table"))
            .where(condition)
            .getSQL();
        System.out.println(sql);
    }
}