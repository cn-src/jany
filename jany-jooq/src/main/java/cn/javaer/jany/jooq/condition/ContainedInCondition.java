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