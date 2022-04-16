package cn.javaer.jany.ebean.expression;

import cn.hutool.core.util.ObjectUtil;
import cn.javaer.jany.util.ObjUtils;
import io.ebean.ExpressionFactory;

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
        factory.arrayContains(property, ObjUtils.toObjectArray(value))),
    arrayNotContains((factory, property, value) ->
        factory.arrayNotContains(property, ObjUtils.toObjectArray(value))),
    eq(ExpressionFactory::eq),

    ne(ExpressionFactory::ne),
    ieq((factory, property, value) ->
        factory.ieq(property, ObjectUtil.toString(value))),
    ine((factory, property, value) ->
        factory.ine(property, ObjectUtil.toString(value))),
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
        factory.like(property, ObjUtils.castString(value))),
    ilike((factory, property, value) ->
        factory.ilike(property, ObjUtils.castString(value))),
    startsWith((factory, property, value) ->
        factory.startsWith(property, ObjUtils.castString(value))),
    istartsWith((factory, property, value) ->
        factory.istartsWith(property, ObjUtils.castString(value))),
    endsWith((factory, property, value) ->
        factory.endsWith(property, ObjUtils.castString(value))),
    iendsWith((factory, property, value) ->
        factory.iendsWith(property, ObjUtils.castString(value))),
    contains((factory, property, value) ->
        factory.contains(property, ObjUtils.castString(value))),
    icontains((factory, property, value) ->
        factory.icontains(property, ObjUtils.castString(value))),
    in((factory, property, value) ->
        factory.in(property, ObjUtils.toCollection(value))),
    notIn((factory, property, value) ->
        factory.notIn(property, ObjUtils.toCollection(value)));

    private final WhereExpression.ExprFunction fun;

    Operator(WhereExpression.ExprFunction fun) {
        this.fun = fun;
    }

    public WhereExpression.ExprFunction getFun() {
        return fun;
    }
}