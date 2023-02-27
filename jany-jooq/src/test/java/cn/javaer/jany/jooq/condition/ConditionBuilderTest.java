/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
            .opt(objectField::contains, "object")
            .opt(arrayField::contains, new String[]{"str1", "str2"})
            .opt(dateTimeField::betweenSymmetric, LocalDateTime.now(), LocalDateTime.now())
            .opt(PGDSL::containedIn, arrayField, new String[]{"value"})
            .opt(PGDSL::jsonbContains, jsonbField, "key", "value")
            .opt(Field::eq, nullField, "")
            .build();

        final String sql = DSL.using(SQLDialect.POSTGRES)
            .select(objectField, arrayField, jsonbField)
            .from(DSL.table("demo_table"))
            .where(condition)
            .getSQL();
        System.out.println(sql);
    }
}