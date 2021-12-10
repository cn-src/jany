package cn.javaer.jany.ebean;

import cn.javaer.jany.model.Sort;
import io.ebean.Query;

/**
 * @author cn-src
 */
public interface Dsl {

    /**
     * 排序。
     *
     * @param query query
     * @param sort sort
     * @param <T> t
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
        return query;
    }
}