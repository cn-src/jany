package cn.javaer.jany.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Objects;
import java.util.Optional;

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

        return ReflectionUtils.getClass(annotation).map(it -> element.getAnnotation((Class<Annotation>) it))
            .isPresent();
    }

    /**
     * 获取指定的注解实例，如果当前注解实例就是要获取的，就返回当前注解实例，否则从其元注解上查找获取。
     *
     * @param clazz 要获取的注解类型
     * @param ann 注解的实例
     * @param <T> T
     *
     * @return 返回注解实例
     */
    @Nullable
    static <T extends Annotation> T getAnnotation(final Class<T> clazz, final Annotation ann) {
        if (clazz.equals(ann.annotationType())) {
            @SuppressWarnings("unchecked")
            final T t = (T) ann;
            return t;
        }
        return ann.annotationType().getAnnotation(clazz);
    }

    /**
     * 从多个注解实例中获取指定类型的注解实例，如果当前注解实例就是要获取的，就返回当前注解实例，否则从其元注解上查找获取。
     *
     * @param clazz 要获取的注解类型
     * @param annotations 注解实例
     * @param <T> T
     *
     * @return 返回注解实例
     */
    @Nullable
    static <T extends Annotation> T getAnnotation(final Class<T> clazz,
                                                  final Annotation... annotations) {
        if (ArrayUtil.isEmpty(annotations)) {
            return null;
        }
        for (final Annotation annotation : annotations) {
            if (annotation == null) {
                continue;
            }
            final T ann = getAnnotation(clazz, annotation);
            if (null != ann) {
                return ann;
            }
        }
        return null;
    }

    /**
     * 从被注解的元素中获取指定类型的注解实例，如果当前注解实例就是要获取的，就返回当前注解实例，否则从其元注解上查找获取。
     *
     * @param clazz 要获取的注解类型
     * @param element 被注解的元素
     * @param <T> T
     *
     * @return 返回注解实例
     */
    @Nullable
    static <T extends Annotation> T getAnnotation(final Class<T> clazz,
                                                  final AnnotatedElement element) {
        final Annotation[] annotations = element.getAnnotations();
        for (final Annotation annotation : annotations) {
            final T ann = getAnnotation(clazz, annotation);
            if (null != ann) {
                return ann;
            }
        }
        return null;
    }

    /**
     * 获取元素上指定注解的属性值.
     *
     * @param element 可注解的元素
     * @param annotation 注解
     * @param attributeName 注解的属性名
     *
     * @return 注解的属性值
     */
    @SuppressWarnings("unchecked")
    static Optional<Object> getAnnotationAttributeValue(final AnnotatedElement element,
                                                        final String annotation,
                                                        final String attributeName) {
        Objects.requireNonNull(element);
        Assert.notEmpty(annotation);
        Assert.notEmpty(attributeName);
        return ReflectionUtils.getClass(annotation)
            .map(it -> element.getAnnotation((Class<Annotation>) it))
            .map(it -> {
                try {
                    return it.annotationType().getField(attributeName).get(it);
                }
                catch (final IllegalAccessException | NoSuchFieldException e) {
                    throw new IllegalStateException(e);
                }
            });
    }
}