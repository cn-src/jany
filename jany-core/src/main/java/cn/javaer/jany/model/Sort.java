/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.model;

import cn.javaer.jany.util.StrUtils;
import lombok.Value;
import org.dromara.hutool.core.array.ArrayUtil;
import org.dromara.hutool.core.collection.ListUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.text.StrUtil;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cn-src
 */
public class Sort implements Serializable {

    private static final long serialVersionUID = -7725678213239466317L;

    private static final Sort UNSORTED = Sort.by(new Order[0]);

    public static final Sort DEFAULT = new Sort(Collections.emptyList(), true);

    public static final Direction DEFAULT_DIRECTION = Direction.ASC;

    private final List<Order> orders;

    /**
     * 是否启用通过审计字段（更新时间和创建时间字段）排序，注解了 io.ebean.annotation.WhenCreated 或
     * io.ebean.annotation.WhenModified 的字段。
     * <P/>
     * 最近更新的数据排在前面，其次最近创建的排在前面。
     */
    private final boolean byAudit;

    protected Sort(List<Order> orders) {
        this.orders = orders;
        this.byAudit = false;
    }

    protected Sort(List<Order> orders, boolean byAudit) {
        this.orders = orders;
        this.byAudit = byAudit;
    }

    private Sort(Direction direction, List<String> properties) {

        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException(
                "You have to provide at least one property to sort by!");
        }

        this.orders = properties.stream()
            .map(it -> new Order(direction, it))
            .collect(Collectors.toList());
        this.byAudit = false;
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
            final String[] props = str.split(",");
            final Optional<Direction> opt = props.length > 1 ?
                Direction.fromOptionalString(props[props.length - 1]) : Optional.empty();
            if (opt.isPresent()) {
                for (int i = 0, le = props.length - 1; i < le; i++) {
                    orders.add(new Order(opt.get(), props[i]));
                }
            }
            else {
                for (String prop : props) {
                    orders.add(Order.by(prop));
                }
            }
        }
        return Sort.by(new ArrayList<>(orders));
    }

    public static Sort by(String... properties) {
        Assert.notNull(properties, "Properties must not be null!");

        return properties.length == 0
            ? Sort.unsorted()
            : new Sort(DEFAULT_DIRECTION, Arrays.asList(properties));
    }

    public static Sort by(List<Order> orders) {

        Assert.notNull(orders, "Orders must not be null!");

        return orders.isEmpty() ? Sort.unsorted() : new Sort(orders);
    }

    public static Sort by(Order... orders) {

        Assert.notNull(orders, "Orders must not be null!");

        return new Sort(Arrays.asList(orders));
    }

    public static Sort by(Direction direction, String... properties) {

        Assert.notNull(direction, "Direction must not be null!");
        Assert.notNull(properties, "Properties must not be null!");
        Assert.isTrue(properties.length > 0, "At least one property must be given!");

        return Sort.by(Arrays.stream(properties)
            .map(it -> new Order(direction, it))
            .collect(Collectors.toList()));
    }

    public static Sort unsorted() {
        return UNSORTED;
    }

    public boolean isSorted() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public boolean isUnsorted() {
        return !isSorted();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public boolean isByAudit() {
        return byAudit;
    }

    public Sort and(Sort sort) {

        Assert.notNull(sort, "Sort must not be null!");

        ArrayList<Order> these = ListUtil.of(orders);
        these.addAll(sort.orders);
        return Sort.by(these);
    }

    @Nullable
    public Order getOrderFor(String property) {

        for (Order order : this.orders) {
            if (order.getProperty().equals(property)) {
                return order;
            }
        }

        return null;
    }

    public Iterator<Order> iterator() {
        return this.orders.iterator();
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Sort)) {
            return false;
        }

        Sort that = (Sort) obj;

        return ListUtil.unmodifiable(this.orders).equals(ListUtil.unmodifiable(that.orders));
    }

    @Override
    public int hashCode() {

        int result = 17;
        result = 31 * result + orders.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return isEmpty() ? "UNSORTED" : StrUtils.join(",", orders);
    }

    @SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
    public enum Direction {

        ASC, DESC;

        public boolean isAscending() {
            return this.equals(ASC);
        }

        public boolean isDescending() {
            return this.equals(DESC);
        }

        public static Direction fromString(String value) {

            try {
                return Direction.valueOf(value.toUpperCase(Locale.US));
            }
            catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                    "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case " +
                        "insensitive).", value), e);
            }
        }

        public static Optional<Direction> fromOptionalString(String value) {

            try {
                return Optional.of(fromString(value));
            }
            catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
    }

    public enum NullHandling {

        /**
         * 存储系统自行处理 null 值排序。
         */
        NATIVE,

        /**
         * null 值排最前。
         */
        NULLS_FIRST,

        /**
         * null 值排最后。
         */
        NULLS_LAST
    }

    @Value
    public static class Order implements Serializable {

        private static final long serialVersionUID = 4508487941220443606L;

        private static final boolean DEFAULT_IGNORE_CASE = false;

        private static final NullHandling DEFAULT_NULL_HANDLING = NullHandling.NATIVE;

        Direction direction;

        String property;

        boolean ignoreCase;

        NullHandling nullHandling;

        public Order(@Nullable Direction direction, String property) {
            this(direction, property, DEFAULT_IGNORE_CASE, DEFAULT_NULL_HANDLING);
        }

        public Order(@Nullable Direction direction, String property,
                     NullHandling nullHandlingHint) {
            this(direction, property, DEFAULT_IGNORE_CASE, nullHandlingHint);
        }

        public static Order by(String property) {
            return new Order(DEFAULT_DIRECTION, property);
        }

        public static Order asc(String property) {
            return new Order(Direction.ASC, property, DEFAULT_NULL_HANDLING);
        }

        public static Order desc(String property) {
            return new Order(Direction.DESC, property, DEFAULT_NULL_HANDLING);
        }

        private Order(@Nullable Direction direction, String property, boolean ignoreCase,
                      NullHandling nullHandling) {

            if (StrUtils.isEmpty(property)) {
                throw new IllegalArgumentException("Property must not be null or empty!");
            }

            this.direction = direction == null ? DEFAULT_DIRECTION : direction;
            this.property = property;
            this.ignoreCase = ignoreCase;
            this.nullHandling = nullHandling;
        }
    }
}