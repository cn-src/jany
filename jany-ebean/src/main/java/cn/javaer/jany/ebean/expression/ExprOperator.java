package cn.javaer.jany.ebean.expression;

import cn.hutool.core.util.ObjectUtil;
import cn.javaer.jany.util.ObjUtils;
import io.ebean.ExpressionFactory;

/**
 * @author cn-src
 */
public enum ExprOperator {

    arrayContains((factory, property, value) ->
        factory.arrayContains(property, ObjUtils.toObjectArray(value))),
    arrayNotContains((factory, property, value) ->
        factory.arrayNotContains(property, ObjUtils.toObjectArray(value))),
    eq(ExpressionFactory::eq),

    eqOrNull(ExpressionFactory::eqOrNull),
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
    gtOrNull(ExpressionFactory::gtOrNull),
    geOrNull(ExpressionFactory::geOrNull),
    gt(ExpressionFactory::gt),
    ge(ExpressionFactory::ge),
    ltOrNull(ExpressionFactory::ltOrNull),
    leOrNull(ExpressionFactory::leOrNull),
    lt(ExpressionFactory::lt),
    le(ExpressionFactory::le),
    like((factory, property, value) ->
        factory.like(property, ObjectUtil.toString(value))),
    ilike((factory, property, value) ->
        factory.ilike(property, ObjectUtil.toString(value))),
    startsWith((factory, property, value) ->
        factory.startsWith(property, ObjectUtil.toString(value))),
    istartsWith((factory, property, value) ->
        factory.istartsWith(property, ObjectUtil.toString(value))),
    endsWith((factory, property, value) ->
        factory.endsWith(property, ObjectUtil.toString(value))),
    iendsWith((factory, property, value) ->
        factory.iendsWith(property, ObjectUtil.toString(value))),
    contains((factory, property, value) ->
        factory.contains(property, ObjectUtil.toString(value))),
    icontains((factory, property, value) ->
        factory.icontains(property, ObjectUtil.toString(value))),
    in((factory, property, value) ->
        factory.in(property, ObjUtils.toCollection(value))),
    inOrEmpty((factory, property, value) ->
        factory.inOrEmpty(property, ObjUtils.toCollection(value))),
    notIn((factory, property, value) ->
        factory.notIn(property, ObjUtils.toCollection(value)));

    private final QueryExpr.ExprFunction fun;

    ExprOperator(QueryExpr.ExprFunction fun) {
        this.fun = fun;
    }

    public QueryExpr.ExprFunction getFun() {
        return fun;
    }
}