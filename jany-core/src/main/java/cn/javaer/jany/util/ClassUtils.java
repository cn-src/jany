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

package cn.javaer.jany.util;

import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author cn-src
 */
public class ClassUtils extends ClassUtil {
    public static boolean isNotStatic(Field field) {
        Assert.notNull(field, "Field to provided is null.");
        return !Modifier.isStatic(field.getModifiers());
    }

    public static boolean isNotTransient(Field field) {
        Assert.notNull(field, "Field to provided is null.");
        return !Modifier.isTransient(field.getModifiers());
    }
}