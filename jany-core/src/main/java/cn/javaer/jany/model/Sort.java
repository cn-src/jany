package cn.javaer.jany.model;

import lombok.Value;

import java.util.Collections;
import java.util.List;

/**
 * @author cn-src
 */
@Value
public class Sort {

    public static final Sort DEFAULT = new Sort(true, Collections.emptyList());

    boolean byAudit;

    List<Order> orders;

    public boolean isSorted() {
        return !orders.isEmpty();
    }

    public enum Direction {
        /**
         * 升序
         */
        ASC,
        /**
         * 倒序
         */
        DESC
    }

    @Value
    public static class Order {
        String property;
        Direction direction;
    }
}