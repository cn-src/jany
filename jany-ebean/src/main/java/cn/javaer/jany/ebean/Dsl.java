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