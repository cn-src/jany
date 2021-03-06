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
    public static Optional<String> fieldNameByAnnotation(Class<?> clazz,
                                                         Class<? extends Annotation> annClazz) {
        return Arrays.stream(ReflectUtil.getFields(clazz))
            .filter(it -> it.isAnnotationPresent(annClazz))
            .findFirst().map(Field::getName);
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