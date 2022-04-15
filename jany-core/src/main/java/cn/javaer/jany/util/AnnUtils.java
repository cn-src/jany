package cn.javaer.jany.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author cn-src
 */
public interface AnnUtils {
    /**
     * 判断元素上是否有指定的注解.
     *
     * @param element 可注解的元素
     * @param annotation 注解
     *
     * @return 有指定的注解返回 true.
     */
    @SuppressWarnings("unchecked")
    static boolean isAnnotated(final AnnotatedElement element, final String annotation) {
        Objects.requireNonNull(element);
        Assert.notEmpty(annotation);

        return ReflectUtils.getClass(annotation).map(it -> element.getAnnotation((Class<Annotation>) it))
            .isPresent();
    }

    /**
     * 查找合成的注解实例：
     * 1. 如果当前注解实例就是要获取的类型，就返回当前注解实例；
     * 2. 否则从其元注解上查找获取，然后以当前注解同名的非空属性值优先合成新的注解实例。
     *
     * 注意：仅支持一层元注解，不递归查找。
     *
     * @param clazz 要获取的注解类型
     * @param ann 注解的实例
     * @param <T> T
     *
     * @return 返回注解实例
     */
    @Nullable
    @SuppressWarnings({"unchecked"})
    static <T extends Annotation> T findMergedAnnotation(final Class<T> clazz,
                                                         final Annotation ann) {
        if (clazz.equals(ann.annotationType())) {
            return (T) ann;
        }
        final T meta = ann.annotationType().getAnnotation(clazz);
        if (null == meta) {
            return null;
        }
        final Map<String, Object> annValues = getAnnotationValueMap(ann);
        if (annValues.values().stream().allMatch(ObjectUtil::isEmpty)) {
            return meta;
        }
        final Map<String, Object> metaValues = getAnnotationValueMap(meta);

        for (Map.Entry<String, Object> entry : annValues.entrySet()) {
            if (ObjectUtil.isNotEmpty(entry.getValue()) && metaValues.containsKey(entry.getKey())) {
                metaValues.put(entry.getKey(), entry.getValue());
            }
        }

        InvocationHandler handler = new SynthesizedAnnotationInvocationHandler(meta, metaValues);
        Class<?>[] exposedInterfaces = new Class<?>[]{meta.annotationType()};
        return (T) Proxy.newProxyInstance(meta.getClass().getClassLoader(),
            exposedInterfaces, handler);
    }

    /**
     * 查找合成的注解实例，返回第一个符合的注解。
     *
     * @param clazz 要获取的注解类型
     * @param annotations 注解实例
     * @param <T> T
     *
     * @return 返回注解实例
     *
     * @see #findMergedAnnotation(Class, Annotation)
     */
    @Nullable
    static <T extends Annotation> T findMergedAnnotation(final Class<T> clazz,
                                                         final Annotation... annotations) {
        if (ArrayUtil.isEmpty(annotations)) {
            return null;
        }
        for (final Annotation annotation : annotations) {
            if (annotation == null) {
                continue;
            }
            final T ann = findMergedAnnotation(clazz, annotation);
            if (null != ann) {
                return ann;
            }
        }
        return null;
    }

    /**
     * 查找合成的注解实例，返回第一个符合的注解。
     *
     * @param <T> T
     * @param element 被注解的元素
     * @param clazz 要获取的注解类型
     *
     * @return 返回注解实例
     *
     * @see #findMergedAnnotation(Class, Annotation)
     */
    @Nullable
    static <T extends Annotation> T findMergedAnnotation(final AnnotatedElement element,
                                                         final Class<T> clazz) {
        final Annotation[] annotations = element.getAnnotations();
        for (final Annotation annotation : annotations) {
            final T ann = findMergedAnnotation(clazz, annotation);
            if (null != ann) {
                return ann;
            }
        }
        return null;
    }

    static Map<String, Object> getAnnotationValueMap(final Annotation annotation) {

        final Method[] methods = ReflectUtil.getMethods(annotation.annotationType(), t -> {
            if (ArrayUtil.isEmpty(t.getParameterTypes())) {
                // 只读取无参方法
                final String name = t.getName();
                // 跳过自有的几个方法
                return (!"hashCode".equals(name)) //
                    && (!"toString".equals(name)) //
                    && (!"annotationType".equals(name));
            }
            return false;
        });

        final HashMap<String, Object> result = new HashMap<>(methods.length, 1);
        for (Method method : methods) {
            result.put(method.getName(), ReflectUtil.invoke(annotation, method));
        }
        return result;
    }
}