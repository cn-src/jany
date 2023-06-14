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

package cn.javaer.jany.jooq.field;

import org.jooq.Record;
import org.jooq.*;
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