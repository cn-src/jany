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

package cn.javaer.jany.ebean.expression;

import cn.javaer.jany.util.ArrayUtils;
import cn.javaer.jany.util.CollUtils;
import cn.javaer.jany.util.StrUtils;
import io.ebean.ExpressionFactory;
import org.dromara.hutool.core.util.ObjUtil;

/**
 * @author cn-src
 */
public enum Operator {

    /**
     * 根据字段类型，自动选取对应的操作符。
     */
    auto((factory, property, value) -> {
        if (value instanceof String) {
            return factory.contains(property, (String) value);
        }
        return factory.eq(property, value);
    }),
    arrayContains((factory, property, value) ->
        factory.arrayContains(property, ArrayUtils.toObjectArray(value))),
    arrayNotContains((factory, property, value) ->
        factory.arrayNotContains(property, ArrayUtils.toObjectArray(value))),
    eq(ExpressionFactory::eq),

    ne(ExpressionFactory::ne),
    ieq((factory, property, value) ->
        factory.ieq(property, ObjUtil.toString(value))),
    ine((factory, property, value) ->
        factory.ine(property, ObjUtil.toString(value))),
    inRange((factory, property, value) -> {
        Object[] values = (Object[]) value;
        return factory.inRange(property, values[0], values[1]);
    }),
    between((factory, property, value) -> {
        Object[] values = (Object[]) value;
        return factory.between(property, values[0], values[1]);
    }),
    gt(ExpressionFactory::gt),
    ge(ExpressionFactory::ge),
    lt(ExpressionFactory::lt),
    le(ExpressionFactory::le),
    like((factory, property, value) ->
        factory.like(property, StrUtils.cast(value))),
    ilike((factory, property, value) ->
        factory.ilike(property, StrUtils.cast(value))),
    startsWith((factory, property, value) ->
        factory.startsWith(property, StrUtils.cast(value))),
    istartsWith((factory, property, value) ->
        factory.istartsWith(property, StrUtils.cast(value))),
    endsWith((factory, property, value) ->
        factory.endsWith(property, StrUtils.cast(value))),
    iendsWith((factory, property, value) ->
        factory.iendsWith(property, StrUtils.cast(value))),
    contains((factory, property, value) ->
        factory.contains(property, StrUtils.cast(value))),
    icontains((factory, property, value) ->
        factory.icontains(property, StrUtils.cast(value))),
    in((factory, property, value) ->
        factory.in(property, CollUtils.cast(value))),
    notIn((factory, property, value) ->
        factory.notIn(property, CollUtils.cast(value)));

    private final WhereExpression.ExprFunction fun;

    Operator(WhereExpression.ExprFunction fun) {
        this.fun = fun;
    }

    public WhereExpression.ExprFunction getFun() {
        return fun;
    }
}