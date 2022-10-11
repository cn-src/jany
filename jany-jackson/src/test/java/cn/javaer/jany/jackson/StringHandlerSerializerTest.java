package cn.javaer.jany.jackson;

import cn.javaer.jany.format.Desensitized;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StringHandlerSerializerTest {
    static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        objectMapper.findAndRegisterModules();
    }

    @Test
    void serialize() throws Exception {
        String json = objectMapper.writeValueAsString(new Demo("230101199606195566", "name1"));
        System.out.println(json);
    }

    @Data
    @AllArgsConstructor
    static class Demo {
        @Desensitized(type = Desensitized.Type.idCard)
        String desensitized;

        String name;
    }
}