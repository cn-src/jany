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

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 反射相关的工具类.
 *
 * @author cn-src
 */
public class ReflectUtils extends ReflectUtil {

    /**
     * 获取所有持久化字段。非静态、非瞬态和未使用给定注解的字段。
     *
     * @param beanClass 要处理的bean的类
     * @param transientClass 将字段标记为瞬态的注释类
     *
     * @return 持久化字段。
     */
    public static Field[] getPersistFields(Class<?> beanClass,
                                           Class<? extends Annotation> transientClass) throws SecurityException {
        return ReflectUtil.getFields(beanClass,
            field -> ClassUtils.isNotStatic(field)
                && ClassUtils.isNotTransient(field)
                && !field.isAnnotationPresent(transientClass));
    }

    /**
     * 获取指定 class 中标注了指定注解的字段的名称。
     *
     * @param clazz class
     * @param annClazz 注解
     *
     * @return 字段名称
     */
    public static Optional<String> fieldName(Class<?> clazz,
                                             Class<? extends Annotation> annClazz) {
        return Arrays.stream(ReflectUtil.getFields(clazz))
            .filter(it -> it.isAnnotationPresent(annClazz))
            .findFirst().map(Field::getName);
    }

    /**
     * 获取指定 class 中标注了指定注解的字段的名称。
     *
     * @param clazz class
     * @param annClazz 注解
     *
     * @return 字段名称
     */
    public static Set<String> fieldNames(Class<?> clazz, Class<? extends Annotation> annClazz) {
        return Arrays.stream(ReflectUtil.getFields(clazz))
            .filter(it -> it.isAnnotationPresent(annClazz))
            .map(Field::getName)
            .collect(Collectors.toSet());
    }

    public static Optional<Field> fieldByAnnotation(Class<?> clazz,
                                                    Class<? extends Annotation> annClazz) {
        return Arrays.stream(ReflectUtil.getFields(clazz))
            .filter(it -> it.isAnnotationPresent(annClazz))
            .findFirst();
    }

    /**
     * 获取字段对应的 Getter 方法名称.
     *
     * @param field the field
     *
     * @return string
     */
    public static String toGetterName(final Field field) {
        return boolean.class.equals(field.getType()) ?
            "is" + StrUtils.upperFirst(field.getName()) :
            "get" + StrUtils.upperFirst(field.getName());
    }

    /**
     * 获取字段对应的 Getter 方法名称.
     *
     * @param fieldName the field name
     * @param fieldType the field type
     *
     * @return string
     */
    public static String toGetterName(final String fieldName,
                                      final String fieldType) {
        return boolean.class.getName().equals(fieldType) ?
            "is" + StrUtils.upperFirst(fieldName) :
            "get" + StrUtils.upperFirst(fieldName);
    }

    /**
     * 查找字段对应的 Getter 方法.
     *
     * @param clazz the clazz
     * @param field the field
     *
     * @return the method handle
     */
    public static Optional<MethodHandle> findGetterByField(final Class<?> clazz,
                                                           final Field field) {

        try {
            return Optional.of(MethodHandles.lookup()
                .findVirtual(clazz, toGetterName(field), MethodType.methodType(field.getType())));
        }
        catch (final NoSuchMethodException e) {
            return Optional.empty();
        }
        catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取 class.
     *
     * @param className the class name
     *
     * @return the class
     */
    public static Optional<Class<?>> getClass(final String className) {
        Assert.notEmpty(className);
        try {
            return Optional.of(ClassLoaderUtil.loadClass(className));
        }
        catch (final UtilException e) {
            return Optional.empty();
        }
    }
}