package cn.javaer.jany.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;

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