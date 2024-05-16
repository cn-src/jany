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

package cn.javaer.jany.ebean;

import cn.javaer.jany.model.Page;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.model.Sort;
import io.ebean.PagedList;
import io.ebean.Query;
import io.ebean.typequery.QueryBean;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.util.ObjUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 构建动态查询条件的 Builder 模式类。
 *
 * @author cn-src
 */
public class Qry<T> {

    private Query<T> query;

    private Qry(Query<T> query) {
        this.query = query;
    }

    public <V> Qry<T> opt(@NotNull final Consumer<V> fn, final V value) {
        if (ObjUtil.isEmpty(value)) {
            return this;
        }
        fn.accept(value);
        return this;
    }

    public <V> Step<T, V> optBegin(final V value) {
        return new Step<>(this, value);
    }

    public <V> Qry<T> opt(@NotNull final BiConsumer<V, V> fn, final V value1, final V value2) {
        if (ObjUtil.isEmpty(value1) || ObjUtil.isEmpty(value2)) {
            return this;
        }
        fn.accept(value1, value2);
        return this;
    }

    public Qry<T> fn(@NotNull Runnable fn) {
        Assert.notNull(fn);
        fn.run();
        return this;
    }

    public Qry<T> fn(@NotNull Supplier<Query<T>> fn) {
        Assert.notNull(fn);
        query = fn.get();
        return this;
    }

    public Page<T> page(PageParam pageParam) {
        Dsl.query(query, pageParam);
        final PagedList<T> pagedList = query.findPagedList();
        return Page.of(pagedList.getList(), pagedList.getTotalCount());
    }

    public List<T> list(Sort sort) {
        return Dsl.query(query, sort).findList();
    }

    public List<T> list() {
        return query.findList();
    }

    public T one() {
        return query.findOne();
    }

    public Optional<T> oneOrEmpty() {
        return query.findOneOrEmpty();
    }

    public Query<T> query() {
        return query;
    }

    public static <T> Qry<T> of(Query<T> query) {
        return new Qry<>(query);
    }

    public static <R, T, QR extends QueryBean<T, R>> Qry<T> of(QR rootBean) {
        return new Qry<>(rootBean.query());
    }

    public static class Step<T, V> {
        private final boolean ignore;

        private final V value;

        private final Qry<T> qry;

        public Step(Qry<T> qry, V value) {
            this.ignore = ObjUtil.isEmpty(value);
            this.value = value;
            this.qry = qry;
        }

        public Step<T, V> opt(@NotNull final Consumer<V> fn) {
            if (ignore) {
                return this;
            }
            fn.accept(this.value);
            return this;
        }

        public Step<T, V> opt(@NotNull Runnable fn) {
            if (ignore) {
                return this;
            }
            Assert.notNull(fn);
            fn.run();
            return this;
        }

        public Qry<T> optEnd() {
            return qry;
        }
    }
}