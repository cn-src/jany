package cn.javaer.jany.model;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author cn-src
 */
@Value
public class Sort {

    /**
     * 默认排序条件。
     */
    public static final Sort DEFAULT = new Sort(true, Collections.emptyList());

    /**
     * 是否启用通过审计字段（更新时间和创建时间字段）排序，注解了{@link io.ebean.annotation.WhenCreated} 或
     * {@link io.ebean.annotation.WhenModified} 的字段。
     * <P/>
     * 最近更新的数据排在前面，其次最近创建的排在前面。
     */
    boolean byAudit;

    List<Order> orders;

    public boolean isSorted() {
        return !orders.isEmpty();
    }

    public static Sort parse(String... directions) {
        if (ArrayUtil.isEmpty(directions)) {
            return Sort.DEFAULT;
        }
        Set<Order> orders = new LinkedHashSet<>();
        for (String str : directions) {
            if (StrUtil.isEmpty(str)) {
                continue;
            }
            final String[] sp = str.split(",");
            Direction direction = Direction.ASC;
            if (sp.length > 1) {
                direction = Direction.parse(sp[sp.length - 1]);
            }
            orders.add(new Order(sp[0], direction));
        }
        return new Sort(true, new ArrayList<>(orders));
    }

    public enum Direction {
        /**
         * 升序
         */
        ASC,
        /**
         * 倒序
         */
        DESC;

        public static Direction parse(String direction) {
            if (StrUtil.isEmpty(direction)) {
                return ASC;
            }
            switch (direction.toLowerCase(Locale.ROOT)) {
                case "desc":
                    return DESC;
                case "asc":
                default:
                    return ASC;
            }
        }
    }

    @Value
    public static class Order {

        /**
         * 排序的属性名。
         */
        String property;

        /**
         * 排序的方向（升序和降序）。
         */
        Direction direction;
    }
}