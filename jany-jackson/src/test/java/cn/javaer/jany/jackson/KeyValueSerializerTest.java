package cn.javaer.jany.jackson;

import cn.javaer.jany.model.KeyValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cn-src
 */
class KeyValueSerializerTest {

    static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(KeyValue.class, KeyValueSerializer.INSTANCE);
        module.addDeserializer(KeyValue.class, KeyValueDeserializer.INSTANCE);
        objectMapper.registerModule(module);
    }

    @Test
    void serialize1() {
        final String json = Json.DEFAULT.write(KeyValue.of("k1", 1));
        assertEquals("{\"k1\":1}", json);
    }

    @Test
    void serialize2() throws JsonProcessingException {
        final String json = objectMapper.writeValueAsString(KeyValue.EMPTY);
        assertEquals("{}", json);
        final String json2 = objectMapper.writeValueAsString(KeyValue.of("", 1));
        assertEquals("{\"\":1}", json2);
    }
}