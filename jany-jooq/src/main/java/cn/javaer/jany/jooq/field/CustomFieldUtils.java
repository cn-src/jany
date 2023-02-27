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

package cn.javaer.jany.jooq.field;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.TableImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author cn-src
 */
interface CustomFieldUtils {
    /**
     * 添加字段到 jOOQ 默认字段表中.
     *
     * @param table table
     * @param field field
     */
    static void addToFields(final Table<?> table, final Field<?> field) {
        if (table instanceof TableImpl) {
            try {
                final Method fields0 = TableImpl.class.getDeclaredMethod("fields0");
                fields0.setAccessible(true);
                final Object fields = fields0.invoke(table);
                final Method add = fields.getClass().getDeclaredMethod("add", Field.class);
                add.setAccessible(true);
                add.invoke(fields, field);
            }
            catch (final IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                // TODO
                throw new RuntimeException(e);
            }
        }
    }
}