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

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.array.ArrayUtil;
import org.dromara.hutool.core.lang.Opt;
import org.dromara.hutool.core.reflect.method.MethodUtil;
import org.dromara.hutool.core.util.ObjUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author cn-src
 */
public class AnnotationUtils extends AnnotationUtil {

    public static boolean hasMergedAnnotation(final Class<? extends Annotation> clazz,
                                              final Annotation ann) {
        if (clazz.equals(ann.annotationType())) {
            return true;
        }
        return ann.annotationType().isAnnotationPresent(clazz);
    }

    public static boolean hasMergedAnnotation(
            final Class<? extends Annotation> clazz, final Annotation... annotations) {
        return hasMergedAnnotation(clazz, Arrays.asList(annotations));
    }

    public static boolean hasMergedAnnotation(
            final Class<? extends Annotation> clazz, final AnnotatedElement element) {
        return hasMergedAnnotation(clazz, element.getAnnotations());
    }

    public static boolean hasMergedAnnotation(
            final Class<? extends Annotation> clazz, Iterable<Annotation> annotations) {
        if (!annotations.iterator().hasNext()) {
            return false;
        }
        for (Annotation annotation : annotations) {
            if (hasMergedAnnotation(clazz, annotation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找合成的注解实例：
     * 1. 如果当前注解实例就是要获取的类型，就返回当前注解实例，否则从其元注解上查找获取；
     * 2. 支持同名属性合并，如果原注解属性值不为空，则用其覆盖元注解，合并生成新注解。
     * <p>
     * 注意：仅支持一层元注解，不递归查找。
     *
     * @param clazz 要获取的注解类型
     * @param ann   注解的实例
     * @param <T>   T
     * @return 返回注解实例
     */
    @SuppressWarnings({"unchecked"})
    public static <T extends Annotation> Opt<T> findMergedAnnotation(final Class<T> clazz,
                                                                     final Annotation ann) {
        if (clazz.equals(ann.annotationType())) {
            return Opt.of((T) ann);
        }
        final T meta = ann.annotationType().getAnnotation(clazz);
        if (null == meta) {
            return Opt.empty();
        }
        final Map<String, Object> annValues = getAnnotationValueMap(ann);
        if (annValues.values().stream().allMatch(ObjUtil::isEmpty)) {
            return Opt.of(meta);
        }
        final Map<String, Object> metaValues = getAnnotationValueMap(meta);

        for (Map.Entry<String, Object> entry : annValues.entrySet()) {
            if (ObjUtil.isNotEmpty(entry.getValue()) && metaValues.containsKey(entry.getKey())) {
                metaValues.put(entry.getKey(), entry.getValue());
            }
        }

        InvocationHandler handler = new SynthesizedAnnotationInvocationHandler(meta, metaValues);
        Class<?>[] exposedInterfaces = new Class<?>[]{meta.annotationType()};
        return Opt.of((T) Proxy.newProxyInstance(meta.getClass().getClassLoader(),
                exposedInterfaces, handler));
    }

    public static <T extends Annotation> Opt<T> findMergedAnnotation(
            final Class<T> clazz, final Stream<Annotation> annotations) {

        return annotations
                .filter(Objects::nonNull)
                .map(ann -> findMergedAnnotation(clazz, ann))
                .filter(Opt::isPresent)
                .findFirst().orElse(Opt.empty());
    }

    /**
     * 查找合成的注解实例，返回第一个符合的注解。
     *
     * @param clazz       要获取的注解类型
     * @param annotations 注解实例
     * @param <T>         T
     * @return 返回注解实例
     * @see #findMergedAnnotation(Class, Annotation)
     */
    public static <T extends Annotation> Opt<T> findMergedAnnotation(final Class<T> clazz,
                                                                     final Annotation... annotations) {
        if (ArrayUtil.isEmpty(annotations)) {
            return Opt.empty();
        }
        return findMergedAnnotation(clazz, Arrays.stream(annotations));
    }

    public static <T extends Annotation> Opt<T> findMergedAnnotation(
            final Class<T> clazz, Iterable<Annotation> annotations) {
        return findMergedAnnotation(clazz, StreamSupport.stream(annotations.spliterator(), false));
    }

    /**
     * 查找合成的注解实例，返回第一个符合的注解。
     *
     * @param <T>     T
     * @param element 被注解的元素
     * @param clazz   要获取的注解类型
     * @return 返回注解实例
     * @see #findMergedAnnotation(Class, Annotation)
     */
    public static <T extends Annotation> Opt<T> findMergedAnnotation(
            final Class<T> clazz, final AnnotatedElement element) {
        return findMergedAnnotation(clazz, element.getAnnotations());
    }

    /**
     * 将注解转换成 Map 形式，key 为注解属性名，value 为注解属性值。
     *
     * @param annotation 要转换的注解对象
     * @return 注释值的映射。
     */
    public static Map<String, Object> getAnnotationValueMap(final Annotation annotation) {
        final HashMap<String, Object> result = new HashMap<>();
        MethodUtil.getMethods(annotation.annotationType(), t -> {
            if (ObjectUtils.anyEquals(t.getName(), "hashCode", "toString", "annotationType")) {
                return false;
            }
            if (ArrayUtil.isNotEmpty(t.getParameterTypes())) {
                return false;
            }
            result.put(t.getName(), MethodUtil.invoke(annotation, t));
            return true;
        });
        return result;
    }
}