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

import org.dromara.hutool.core.util.ObjUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Condition 条件构建器, 会忽略一些空值的条件.
 *
 * @author cn-src
 */
public class ConditionBuilder {

    private final List<Condition> conditions = new ArrayList<>();

    private Condition defaultCondition;

    public ConditionBuilder opt(final Condition condition) {
        if (null != condition) {
            this.conditions.add(condition);
        }
        return this;
    }

    public ConditionBuilder opt(final boolean isAppend,
                                @NotNull final Supplier<@NotNull Condition> supplier) {
        if (isAppend) {
            return this.required(supplier);
        }
        return this;
    }

    public ConditionBuilder optNotNull(final Field<?>... fields) {
        if (null != fields && fields.length > 0) {
            if (fields.length == 1) {
                this.conditions.add(fields[0].isNotNull());
            }
            else {
                Arrays.stream(fields).forEach(field -> this.conditions.add(field.isNotNull()));
            }
        }
        return this;
    }

    public ConditionBuilder opt(@NotNull final Supplier<@Nullable Condition> supplier) {
        this.opt(supplier.get());
        return this;
    }

    public ConditionBuilder required(@NotNull final Condition condition) {
        this.conditions.add(Objects.requireNonNull(condition));
        return this;
    }

    public ConditionBuilder required(@NotNull final Supplier<@NotNull Condition> supplier) {
        this.conditions.add(Objects.requireNonNull(supplier.get()));
        return this;
    }
//
//    @SafeVarargs
//    public final ConditionBuilder optionalValue(final TreeNode treeNode,
//                                                @NotNull final Field<String>... fields) {
//        if (fields == null || fields.length == 0) {
//            throw new IllegalArgumentException("'fields' must be not empty");
//        }
//        return this.optional(ConditionCreator.create(treeNode, fields));
//    }
//
//    @SafeVarargs
//    public final ConditionBuilder optionalValue(final List<TreeNode> treeNodes,
//                                                @NotNull final Field<String>... fields) {
//        if (fields == null || fields.length == 0) {
//            throw new IllegalArgumentException("'fields' must be not empty");
//        }
//
//        return this.optional(ConditionCreator.create(treeNodes, fields));
//    }
//
//    @SafeVarargs
//    public final ConditionBuilder optional(final TreeNode treeNode, final Field<String>...
//        fields) {
//        if (null == treeNode || ObjectUtil.isEmpty(fields)) {
//            return this;
//        }
//        return this.optional(ConditionCreator.create(treeNode, fields));
//    }
//
//    @SafeVarargs
//    public final ConditionBuilder optional(final List<TreeNode> treeNodes,
//                                           final Field<String>... fields) {
//        if (CollUtil.isEmpty(treeNodes) || ObjectUtil.isEmpty(fields)) {
//            return this;
//        }
//        return this.optional(ConditionCreator.create(treeNodes, fields));
//    }

    public <T> ConditionBuilder opt(@NotNull final Function<T, Condition> fun, final T
            value) {
        if (ObjUtil.isEmpty(value)) {
            return this;
        }

        this.conditions.add(fun.apply(value));
        return this;
    }

    public <T1, T2> ConditionBuilder opt(@NotNull final BiFunction<T1, T2, Condition> fun,
                                         final T1 t1, final T2 t2) {
        if (ObjUtil.isEmpty(t1) || ObjUtil.isEmpty(t2)) {
            return this;
        }
        this.conditions.add(fun.apply(t1, t2));
        return this;
    }

    public <T1, T2, T3> ConditionBuilder opt(@NotNull final Function3<T1, T2, T3> fun,
                                             final T1 t1, final T2 t2, final T3 t3) {
        if (ObjUtil.isEmpty(t1) || ObjUtil.isEmpty(t2) || ObjUtil.isEmpty(t3)) {
            return this;
        }
        this.conditions.add(fun.apply(t1, t2, t3));
        return this;
    }

    public ConditionBuilder trueConditionIfEmpty() {
        this.defaultCondition = DSL.trueCondition();
        return this;
    }

    public ConditionBuilder falseConditionIfEmpty() {
        this.defaultCondition = DSL.falseCondition();
        return this;
    }

    @Nullable
    public Condition build() {
        if (this.conditions.isEmpty()) {
            return this.defaultCondition;
        }
        Condition condition = this.conditions.get(0);
        for (int i = 1, size = this.conditions.size(); i < size; i++) {
            condition = condition.and(this.conditions.get(i));
        }
        return condition;
    }

    @FunctionalInterface
    public interface Function3<T1, T2, T3> {
        /**
         * The three parameters Function.
         *
         * @param t1 t1
         * @param t2 t2
         * @param t3 t3
         * @return Condition
         */
        Condition apply(T1 t1, T2 t2, T3 t3);
    }
}