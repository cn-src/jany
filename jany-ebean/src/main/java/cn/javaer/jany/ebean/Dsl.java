package cn.javaer.jany.ebean;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.model.Sort;
import cn.javaer.jany.util.ReflectionUtils;
import io.ebean.Query;
import io.ebean.UpdateQuery;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;

import java.util.Map;

/**
 * @author cn-src
 */
public interface Dsl {

    /**
     * Page query.
     *
     * @param <T> the type parameter
     * @param query the query
     * @param pageParam the page param
     *
     * @return the query
     */
    static <T> Query<T> query(Query<T> query, PageParam pageParam) {
        return query(query, pageParam.getSort())
            .setMaxRows(pageParam.getSize())
            .setFirstRow(pageParam.getOffset());
    }

    /**
     * Sort query.
     *
     * @param <T> the type parameter
     * @param query the query
     * @param sort the sort
     *
     * @return the query
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
        }
        if (sort.isByAudit()) {
            final Class<T> beanType = query.getBeanType();
            ReflectionUtils.fieldNameByAnnotation(beanType, WhenModified.class)
                .ifPresent(fieldName -> query.orderBy().desc(fieldName));
            ReflectionUtils.fieldNameByAnnotation(beanType, WhenCreated.class)
                .ifPresent(fieldName -> query.orderBy().desc(fieldName));
        }
        return query;
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
    static <T> UpdateQuery<T> update(UpdateQuery<T> updateQuery, Object obj) {
        final Map<String, Object> beanMap = BeanUtil.beanToMap(obj);
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            if (ObjectUtil.isEmpty(entry.getValue())) {
                updateQuery.setNull(entry.getKey());
            }
            else {
                updateQuery.set(entry.getKey(), entry.getValue());
            }
        }
        return updateQuery;
    }
}