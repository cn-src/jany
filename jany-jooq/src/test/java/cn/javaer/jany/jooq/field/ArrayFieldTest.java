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