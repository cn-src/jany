package cn.javaer.jany.spring.util;

import cn.javaer.jany.model.PageParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author cn-src
 */
public interface PageUtils {

    /**
     * spring data Pageable 转 jany PageParam。
     *
     * @param pageable Pageable
     *
     * @return PageParam
     */
    static PageParam of(final Pageable pageable) {
        final Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            Set<cn.javaer.jany.model.Sort.Order> orders = new LinkedHashSet<>();
            for (Sort.Order order : sort) {
                final cn.javaer.jany.model.Sort.Direction direction =
                    cn.javaer.jany.model.Sort.Direction.valueOf(order.getDirection().name());
                orders.add(new cn.javaer.jany.model.Sort.Order(order.getProperty(), direction));
            }
            return new PageParam(pageable.getPageNumber() + 1, pageable.getPageSize(),
                new cn.javaer.jany.model.Sort(true, new ArrayList<>(orders)));
        }
        return PageParam.of(pageable.getPageNumber() + 1, pageable.getPageSize());
    }
}