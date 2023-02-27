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

package cn.javaer.jany.jooq;

import cn.javaer.jany.type.Geometry;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.jooq.exception.DataAccessException;
import org.postgresql.util.PGobject;

import java.sql.SQLException;

/**
 * @author cn-src
 */
public enum PostGISGeometryConverter implements Converter<PGobject, Geometry> {

    /**
     * 单实例
     */
    INSTANCE;

    private static final long serialVersionUID = 599360862926272439L;

    @Override
    public Geometry from(final PGobject db) {

        return null == db ? null : Geometry.valueOf(db.getValue());
    }

    @Override
    public PGobject to(final Geometry userObject) {
        if (null == userObject) {
            return null;
        }
        final PGobject pg = new PGobject();
        try {
            pg.setType("geometry");
            pg.setValue(userObject.data());
        }
        catch (final SQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
        return pg;
    }

    @Override
    public @NotNull Class<PGobject> fromType() {
        return PGobject.class;
    }

    @Override
    public @NotNull Class<Geometry> toType() {
        return Geometry.class;
    }
}