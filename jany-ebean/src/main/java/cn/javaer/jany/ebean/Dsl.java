package cn.javaer.jany.ebean;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.javaer.jany.ebean.expression.Operator;
import cn.javaer.jany.ebean.expression.Type;
import cn.javaer.jany.ebean.expression.WhereExpression;
import cn.javaer.jany.ebean.expression.WhereIgnore;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.model.Sort;
import cn.javaer.jany.util.AnnotationUtils;
import cn.javaer.jany.util.ReflectUtils;
import io.ebean.ExpressionFactory;
import io.ebean.ExpressionList;
import io.ebean.Query;
import io.ebean.UpdateQuery;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author cn-src
 */
public interface Dsl {

    /**
     * 对已有的查询 query 添加分页条件部分。
     *
     * @param <T> 实体类型
     * @param query 查询
     * @param pageParam 分页条件
     *
     * @return 添加分页条件的查询
     */
    static <T> Query<T> query(Query<T> query, PageParam pageParam) {
        return query(query, pageParam.getSort())
            .setMaxRows(pageParam.getSize())
            .setFirstRow(pageParam.getOffset());
    }

    /**
     * 对已有的查询 query 添加排序条件部分。
     * <p>
     * 排序规则优先级（只使用其中一种规则）：
     * <p>1. 按显示指定的属性排序；
     * <p>2. 按 @SortBy 注解的属性排序；
     * <p>3. 按审计字段排序。
     *
     * @param <T> 实体类型
     * @param query 查询
     * @param sort 排序条件
     *
     * @return 添加排序条件的查询
     */
    static <T> Query<T> query(Query<T> query, Sort sort) {
        if (sort.isSorted()) {
            for (Sort.Order order : sort.getOrders()) {
                if (Sort.Direction.ASC.equals(order.getDirection())) {
                    query.orderBy().asc(order.getProperty());
                }
                else if (Sort.Direction.DESC.equals(order.getDirection())) {
                    query.orderBy().desc(order.getProperty());
                }
            }
            return query;
        }

        final Class<T> beanType = query.getBeanType();
        final Optional<Field> sortOpt = ReflectUtils.fieldByAnnotation(beanType, SortBy.class);
        if (sortOpt.isPresent()) {
            Field field = sortOpt.get();
            Sort.Direction direction = field.getAnnotation(SortBy.class).direction();
            switch (direction) {
                case ASC:
                    return query.orderBy().asc(field.getName());
                case DESC:
                    return query.orderBy().desc(field.getName());
                default:
                    throw new IllegalStateException();
            }
        }

        if (sort.isByAudit()) {
            ReflectUtils.fieldName(beanType, WhenModified.class)
                .ifPresent(fieldName -> query.orderBy().desc(fieldName));
            ReflectUtils.fieldName(beanType, WhenCreated.class)
                .ifPresent(fieldName -> query.orderBy().desc(fieldName));
            return query;
        }
        return query;
    }

    /**
     * 对已有的查询 query，添加示例对象查询条件。
     *
     * @param query 待处理的查询对象
     * @param example 要查询的对象。
     *
     * @return 查询对象
     */
    static <T> Query<T> queryExample(Query<T> query, Object example) {
        final ExpressionFactory factory = query.getExpressionFactory();
        final ExpressionList<T> where = query.where();
        Map<String, RangeStore> rangeMap = new HashMap<>();
        ReflectUtil.getFieldMap(example.getClass()).forEach((fieldName, field) -> {
            final Object value = ReflectUtil.getFieldValue(example, fieldName);
            if (ObjectUtil.isNotEmpty(value) && !field.isAnnotationPresent(WhereIgnore.class)
                && !fieldName.startsWith("$")) {
                final WhereExpression whereExpression = AnnotationUtils.findMergedAnnotation(WhereExpression.class, field)
                    .orElse(WhereExpression.DEFAULT);

                final Type type = whereExpression.type();
                String property = ObjectUtil.defaultIfEmpty(whereExpression.property(), fieldName);

                if (type == Type.DEFAULT) {
                    where.add(whereExpression.value().getFun().apple(factory, property, value));
                }
                else if (type == Type.RANGE_START) {
                    rangeMap.compute(property, (k, v) -> v == null ?
                        new RangeStore().setOperator(whereExpression.value()).setStartValue(value)
                        : v.setStartValue(value));
                }
                else if (type == Type.RANGE_END) {
                    rangeMap.compute(property, (k, v) -> v == null ?
                        new RangeStore().setOperator(whereExpression.value()).setEndValue(value)
                        : v.setEndValue(value));
                }
            }
        });
        for (Map.Entry<String, RangeStore> entry : rangeMap.entrySet()) {
            where.add(entry.getValue().operator.getFun().apple(factory, entry.getKey(),
                new Object[]{entry.getValue().startValue, entry.getValue().endValue}));
        }
        return query;
    }

    @Data
    @Accessors(chain = true)
    class RangeStore {
        private Operator operator;

        private Object startValue;

        private Object endValue;
    }

    /**
     * 对已有的更新查询 updateQuery 添加 SQL SET 部分，其不会忽略空值，如果是空值将会设置为 null。
     *
     * @param <T> 实体类型
     * @param updateQuery 更新查询
     * @param obj 值对象
     *
     * @return 添加 SQL SET 部分的更新查询
     */
    static <T> UpdateQuery<T> update(UpdateQuery<T> updateQuery, Object obj,
                                     boolean ignoreEmpty) {
        final Map<String, Object> beanMap = BeanUtil.beanToMap(obj);
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            if (!ignoreEmpty && ObjectUtil.isEmpty(entry.getValue())) {
                updateQuery.setNull(entry.getKey());
            }
            else {
                updateQuery.set(entry.getKey(), entry.getValue());
            }
        }
        return updateQuery;
    }
}