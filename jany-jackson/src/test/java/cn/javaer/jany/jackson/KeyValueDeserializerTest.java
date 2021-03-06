package cn.javaer.jany.jackson;

import cn.javaer.jany.model.KeyValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cn-src
 */
class KeyValueDeserializerTest {
    static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(KeyValue.class, KeyValueSerializer.INSTANCE);
        module.addDeserializer(KeyValue.class, KeyValueDeserializer.INSTANCE);
        objectMapper.registerModule(module);
    }

    @Test
    void deserialize1() {
        //language=JSON
        final KeyValue<String> keyValue = Json.DEFAULT.read("{\"name\": \"value1\"}",
            new TypeReference<KeyValue<String>>() {});
        assertThat(keyValue.getKey()).isEqualTo("name");
        assertThat(keyValue.getValue()).isEqualTo("value1");
    }

    @Test
    void deserialize2() throws JsonProcessingException {
        final KeyValue<String> keyValue = objectMapper.readValue("{}",
            new TypeReference<KeyValue<String>>() {});
        assertThat(keyValue.getKey()).isNull();
        assertThat(keyValue.getValue()).isNull();
    }

    @Test
    void deserialize3() {
        assertThrows(MismatchedInputException.class, () -> {
            objectMapper.readValue("[]",
                new TypeReference<KeyValue<String>>() {});
        });
    }

    @Test
    void deserialize4() {
        assertThrows(MismatchedInputException.class, () -> {
            //language=JSON
            objectMapper.readValue("{\"name\": \"value1\", \"value2\": \"value2\"}",
                new TypeReference<KeyValue<String>>() {});
        });
    }

    @Test
    void deserialize5() throws JsonProcessingException {
        final KeyValue<String> keyValue = objectMapper.readValue("{\"name\": \"value1\"}",
            new TypeReference<KeyValue<String>>() {});
        assertThat(keyValue.getKey()).isEqualTo("name");
        assertThat(keyValue.getValue()).isEqualTo("value1");
    }

    @Test
    void deserialize6() throws JsonProcessingException {
        final KeyValue<?> keyValue = objectMapper.readValue("{\"name\": \"value1\"}",
            new TypeReference<KeyValue<?>>() {});
        assertThat(keyValue.getKey()).isEqualTo("name");
        assertThat(keyValue.getValue()).isEqualTo("value1");
    }

    @Test
    void deserialize7() throws JsonProcessingException {
        final KeyValue<?> keyValue = objectMapper.readValue("{\"name\": \"value1\"}",
            KeyValue.class);
        assertThat(keyValue.getKey()).isEqualTo("name");
        assertThat(keyValue.getValue()).isEqualTo("value1");
    }

    @Test
    void deserialize8() throws JsonProcessingException {
        //language=JSON
        final KeyValue<List<String>> keyValue = objectMapper.readValue("{\"name\": [\"value1\"]}",
            new TypeReference<KeyValue<List<String>>>() {});
        assertThat(keyValue.getKey()).isEqualTo("name");
        assertThat(keyValue.getValue()).hasSize(1).containsExactly("value1");
    }
}