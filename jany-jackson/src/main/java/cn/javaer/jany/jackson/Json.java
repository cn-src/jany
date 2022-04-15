package cn.javaer.jany.jackson;

import cn.hutool.core.util.StrUtil;
import cn.javaer.jany.model.KeyValue;
import cn.javaer.jany.util.ReflectUtils;
import cn.javaer.jany.util.TimeUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.jooq.JSONB;
import org.jooq.Record;

import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public static final SimpleModule MODULE = new SimpleModule();

    static {
        // @formatter:off
        MODULE.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(TimeUtils.DATE_TIME_FORMATTER));
        MODULE.addSerializer(LocalDate.class, new LocalDateSerializer(TimeUtils.DATE_FORMATTER));
        MODULE.addSerializer(LocalTime.class, new LocalTimeSerializer(TimeUtils.TIME_FORMATTER));
        MODULE.addSerializer(KeyValue.class, KeyValueSerializer.INSTANCE);

        MODULE.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(TimeUtils.DATE_TIME_FORMATTER));
        MODULE.addDeserializer(LocalDate.class, new LocalDateDeserializer(TimeUtils.DATE_FORMATTER));
        MODULE.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TimeUtils.TIME_FORMATTER));
        MODULE.addDeserializer(KeyValue.class, KeyValueDeserializer.INSTANCE);

        // @formatter:on

        ReflectUtils.getClass("org.jooq.JSONB").ifPresent(it -> {
            @SuppressWarnings({"unchecked"})
            final Class<JSONB> clazz = (Class<JSONB>) it;
            MODULE.addSerializer(clazz, JooqJsonbSerializer.INSTANCE);
            MODULE.addDeserializer(clazz, JooqJsonbDeserializer.INSTANCE);
        });
        ReflectUtils.getClass("org.jooq.Record").ifPresent(it -> {
            @SuppressWarnings({"unchecked"})
            final Class<Record> clazz = (Class<Record>) it;
            MODULE.addSerializer(clazz, JooqRecordSerializer.INSTANCE);
        });

        final ObjectMapper aDefault = new ObjectMapper();
        aDefault.setAnnotationIntrospector(DateTimeFormatIntrospector.INSTANCE);
        aDefault.registerModule(MODULE);
        DEFAULT = new Json(aDefault);

        final ObjectMapper lenient = new ObjectMapper();
        lenient.setAnnotationIntrospector(DateTimeFormatIntrospector.INSTANCE);
        lenient.registerModule(MODULE);
        lenient.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LENIENT = new Json(aDefault);

        final ObjectMapper nonEmpty = new ObjectMapper();
        nonEmpty.setAnnotationIntrospector(DateTimeFormatIntrospector.INSTANCE);
        nonEmpty.registerModule(MODULE);
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

    public String write(final Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        }
        catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

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