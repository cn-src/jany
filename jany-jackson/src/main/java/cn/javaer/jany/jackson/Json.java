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

package cn.javaer.jany.jackson;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jetbrains.annotations.Nullable;

import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 基于 jackson 的工具类，与常规的纯静态方法的工具类设计的不同之处在于，可传入不同的 ObjectMapper
 * 来实例化新的工具对象，方便定制扩展，同时提供静态常量实例来方便直接使用。
 *
 * <p>
 * 此工具目前主要是对受检查异常进行转换抛出。
 * </p>
 *
 * @author cn-src
 */
public class Json {

    /**
     * 默认实例.
     */
    public static final Json DEFAULT;

    /**
     * 宽松模式，反序列化忽略未知字段.
     */
    public static final Json LENIENT;

    /**
     * 序列化时忽略空对象的实例.
     */
    public static final Json NON_EMPTY;

    static {
        Module[] modules = {new JavaTimeModule(), new Jdk8Module(), new JanyModule()};
        final ObjectMapper aDefault = new ObjectMapper();
        aDefault.registerModules(modules);
        DEFAULT = new Json(aDefault);

        final ObjectMapper lenient = new ObjectMapper();
        lenient.registerModules(modules);
        lenient.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LENIENT = new Json(lenient);

        final ObjectMapper nonEmpty = new ObjectMapper();
        nonEmpty.registerModules(modules);
        nonEmpty.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        NON_EMPTY = new Json(nonEmpty);
    }

    private final ObjectMapper objectMapper;

    /**
     * 根据 ObjectMapper 创建新实例。
     *
     * @param objectMapper ObjectMapper
     */
    public Json(final ObjectMapper objectMapper) {
        Objects.requireNonNull(objectMapper);
        this.objectMapper = objectMapper;
    }

    public ObjectMapper objectMapper() {
        return objectMapper;
    }

    /**
     * 将对象序列化为 JSON 字符串。
     * <p>
     * 如果对象为 null，则返回 null。
     *
     * @param obj 要序列化的对象。
     *
     * @return 对象的 JSON 字符串。
     */
    public String write(final Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return this.objectMapper.writeValueAsString(obj);
        }
        catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为对象。
     * <p>
     * 如果 JSON 字符串为空，则返回 null。
     *
     * @param json 要反序列化的 JSON 字符串。
     * @param clazz 要反序列化的对象的类。
     *
     * @return T 类型的通用对象
     */
    @Nullable
    public <T> T read(final String json, final Class<T> clazz) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return this.objectMapper.readValue(json, clazz);
        }
        catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为对象。
     * <p>
     * 如果 JSON 字符串为空，则返回 null。
     *
     * @param json 要反序列化的 JSON 字符串。
     * @param valueTypeRef 要反序列化的对象的类型。
     *
     * @return 泛型类型 T
     */
    public <T> T read(final String json, final TypeReference<T> valueTypeRef) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return this.objectMapper.readValue(json, valueTypeRef);
        }
        catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为对象 List。
     * <p>
     * 如果 JSON 字符串为空，则返回 null。
     *
     * @param json 要反序列化的 JSON 字符串。
     * @param clazz 要转换的对象的类。
     *
     * @return 类型 T 的对象列表。
     */
    public <T> List<T> readList(final String json, final Class<T> clazz) {
        if (StrUtil.isEmpty(json)) {
            return Collections.emptyList();
        }
        JavaType javaType =
            objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return this.objectMapper.readValue(json, javaType);
        }
        catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 将 JSON 字符串解析为 JsonNode。
     * <p>
     * 如果 JSON 字符串为空，则返回 null。
     *
     * @param json 要解析的 JSON 字符串。
     *
     * @return 一个 JsonNode 对象
     */
    public JsonNode read(final String json) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return this.objectMapper.readTree(json);
        }
        catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}