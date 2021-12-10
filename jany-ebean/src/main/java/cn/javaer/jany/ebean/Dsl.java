package cn.javaer.jany.ebean;

import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.model.Sort;
import cn.javaer.jany.util.ReflectionUtils;
import io.ebean.Query;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;

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
    static <T> Query<T> page(Query<T> query, PageParam pageParam) {
        return sort(query, pageParam.getSort())
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
    static <T> Query<T> sort(Query<T> query, Sort sort) {
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
}