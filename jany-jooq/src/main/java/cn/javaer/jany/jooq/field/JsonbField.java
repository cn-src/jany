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

import cn.javaer.jany.jackson.Json;
import org.jooq.Record;
import org.jooq.*;
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