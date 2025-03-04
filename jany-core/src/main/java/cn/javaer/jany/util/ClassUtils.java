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
    /**
     * 检查给定的字段是否不是静态的
     *
     * @param field 要检查的字段，不应为null
     * @return 如果字段不是静态的，则返回true；否则返回false
     * <p>
     * 此方法首先确保提供的字段对象不为null，然后检查字段的修饰符是否包含静态（static）
     * 它通过调用Field对象的getModifiers方法并使用Modifier类的静态方法isStatic来判断
     */
    public static boolean isNotStatic(Field field) {
        // 确保提供的字段对象不为null，如果为null，则抛出IllegalArgumentException
        Assert.notNull(field, "Field to provided is null.");
        // 返回字段是否不是静态的
        return !Modifier.isStatic(field.getModifiers());
    }

    /**
     * 判断给定的字段是否不是瞬态的
     * 该方法主要用于检查给定的字段是否没有被transient关键字修饰
     * transient关键字用于防止字段被序列化
     *
     * @param field 要检查的字段对象，用于反射获取字段的修饰符
     * @return 如果字段不是瞬态的，则返回true；否则返回false
     * @throws NullPointerException 如果提供的field参数为null，根据Assert.notNull的实现，会抛出此异常
     */
    public static boolean isNotTransient(Field field) {
        // 确保提供的字段对象不为null
        Assert.notNull(field, "Field to provided is null.");
        // 返回字段是否不是瞬态的
        return !Modifier.isTransient(field.getModifiers());
    }
}