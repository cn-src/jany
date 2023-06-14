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

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.Transaction;
import io.ebean.bean.EntityBean;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;


/**
 * 相对于官方的 Model 类部分方法改成了链式调用。
 *
 * @author cn-src
 */
@MappedSuperclass
@SuppressWarnings("unchecked")
public abstract class BaseModel<E> {

    @Transient
    private final String _$dbName;

    public BaseModel() {
        this._$dbName = null;
    }

    public BaseModel(String dbName) {
        this._$dbName = dbName;
    }

    public Database db() {
        return DB.byName(_$dbName);
    }

    public E markAsDirty() {
        db().markAsDirty(this);
        return (E) this;
    }

    public E markPropertyUnset(String propertyName) {
        ((EntityBean) this)._ebean_getIntercept().setPropertyLoaded(propertyName, false);
        return (E) this;
    }

    public E save() {
        db().save(this);
        return (E) this;
    }

    public E save(Transaction transaction) {
        db().save(this, transaction);
        return (E) this;
    }

    public E flush() {
        db().flush();
        return (E) this;
    }

    public E update() {
        db().update(this);
        return (E) this;
    }

    public E update(Transaction transaction) {
        db().update(this, transaction);
        return (E) this;
    }

    public E insert() {
        db().insert(this);
        return (E) this;
    }

    public E insert(Transaction transaction) {
        db().insert(this, transaction);
        return (E) this;
    }

    public boolean delete() {
        return db().delete(this);
    }

    public boolean delete(Transaction transaction) {
        return db().delete(this, transaction);
    }

    public boolean deletePermanent() {
        return db().deletePermanent(this);
    }

    public boolean deletePermanent(Transaction transaction) {
        return db().deletePermanent(this, transaction);
    }

    public E refresh() {
        db().refresh(this);
        return (E) this;
    }
}