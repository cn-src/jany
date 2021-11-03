package cn.javaer.jany.test;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.javaer.jany.jackson.Json;

import java.util.List;

/**
 * @author cn-src
 */
public interface ResourceUtils {

    /**
     * Read list.
     *
     * @param <T> the type parameter
     * @param resource the resource
     * @param clazz the clazz
     *
     * @return the list
     */
    static <T> List<T> readJson(String resource, Class<T> clazz) {
        return Json.DEFAULT.readList(ResourceUtil.readUtf8Str(resource), clazz);
    }
}