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
public interface ReflectUtils {

    /**
     * 获取指定 class 中标注了指定注解的字段的名称。
     *
     * @param clazz class
     * @param annClazz 注解
     *
     * @return 字段名称
     */
    static Optional<String> fieldNameByAnnotation(Class<?> clazz,
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
    static String toGetterName(final Field field) {
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
    static String toGetterName(final String fieldName,
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
    static Optional<MethodHandle> findGetterByField(final Class<?> clazz, final Field field) {

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
    static Optional<Class<?>> getClass(final String className) {
        Assert.notEmpty(className);
        try {
            return Optional.of(ClassLoaderUtil.loadClass(className));
        }
        catch (final UtilException e) {
            return Optional.empty();
        }
    }
}