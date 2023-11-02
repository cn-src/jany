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
import io.ebean.Finder;
import io.ebean.PagedList;
import io.ebean.Query;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author cn-src
 */
public class BaseFinder<I, T> extends Finder<I, T> {

    public BaseFinder(Class<T> type) {
        super(type);
    }

    public BaseFinder(Class<T> type, String databaseName) {
        super(type, databaseName);
    }

    public BaseFinder() {
        this((String) null);
    }

    public BaseFinder(String databaseName) {
        // noinspection ConstantConditions
        super(null, databaseName);
        final Class<?> clazz = ClassUtil.getTypeArgument(this.getClass(), 1);
        // 必须实现继承才能反射范型
        Assert.isFalse(Object.class.equals(clazz), "Type argument must be not Object");
        FieldUtil.setFieldValue(this, "type", clazz);
    }

    @Override
    public @NotNull List<T> all() {
        return list(Sort.DEFAULT);
    }

    public Query<T> query(PageParam pageParam) {
        return Dsl.query(query(), pageParam);
    }

    public Query<T> query(Sort sort) {
        return Dsl.query(query(), sort);
    }

    public List<T> list(PageParam pageParam) {
        return query(pageParam).findList();
    }

    public List<T> list(Sort sort) {
        return query(sort).findList();
    }

    public int count() {
        return query().findCount();
    }

    public Page<T> page(PageParam pageParam) {
        final PagedList<T> pagedList = query(pageParam).findPagedList();
        return Page.of(pagedList.getList(), pagedList.getTotalCount());
    }

    public Page<T> page(Object example, PageParam pageParam) {
        final Query<T> query = Dsl.queryExample(query(), example);
        final PagedList<T> pagedList = Dsl.query(query, pageParam).findPagedList();
        return Page.of(pagedList.getList(), pagedList.getTotalCount());
    }

    public T save(T bean) {
        db().save(bean);
        return bean;
    }

    public T insert(T bean) {
        db().insert(bean);
        return bean;
    }

    public T update(T bean) {
        db().update(bean);
        return bean;
    }
}