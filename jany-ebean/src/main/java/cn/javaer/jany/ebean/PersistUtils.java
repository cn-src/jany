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

import cn.javaer.jany.util.ClassUtils;
import cn.javaer.jany.util.StrUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.lang.Opt;
import org.dromara.hutool.core.map.reference.WeakConcurrentMap;
import org.dromara.hutool.core.reflect.FieldUtil;

import java.lang.reflect.Field;

/**
 * @author cn-src
 */
public class PersistUtils {
    private static final
    WeakConcurrentMap<Class<?>, Field[]> FIELDS_CACHE = new WeakConcurrentMap<>();

    public static Field[] getPersistFields(Class<?> entityClass) {
        Assert.notNull(entityClass);
        return FIELDS_CACHE.computeIfAbsent(entityClass, (k) -> FieldUtil.getFields(entityClass,
                field -> !field.getName().startsWith("_ebean")
                        && ClassUtils.isNotStatic(field)
                        && ClassUtils.isNotTransient(field)
                        && !field.isAnnotationPresent(Transient.class)));
    }

    public static String tableName(Class<?> entityClass) {
        return Opt.ofNullable(entityClass.getAnnotation(Table.class))
                .map(Table::name)
                .filter(StrUtils::isNotEmpty)
                .orElse(StrUtils.toSnakeLower(entityClass.getSimpleName()));
    }

    public static String columnName(Field field) {
        return Opt.ofNullable(field.getAnnotation(Column.class))
                .map(Column::name)
                .filter(StrUtils::isNotEmpty)
                .orElse(StrUtils.toSnakeLower(field.getName()));
    }
}