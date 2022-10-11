package cn.javaer.jany.jackson;

import cn.javaer.jany.format.Desensitized;
import cn.javaer.jany.format.StringFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StringFormatSerializerTest {
    static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        // objectMapper.registerModules(new JavaTimeModule(), new Jdk8Module(), new JanyModule());
        objectMapper.setAnnotationIntrospector(JanyJacksonAnnotationIntrospector.INSTANCE);
    }

    @Test
    void serialize() throws Exception {
        Demo demo = new Demo("230101199606195566", "str1", "  ", "  str3  ", " str4 ", "  ");
        String json = objectMapper.writeValueAsString(demo);
        System.out.println(json);
    }

    @Data
    @AllArgsConstructor
    @StringFormat
    static class Demo {
        @Desensitized(type = Desensitized.Type.ID_CARD)
        String desensitized;

        String str1;

        String str2;

        String str3;

        @StringFormat(apply = false)
        String str4;

        @StringFormat(emptyToNull = false)
        String str5;
    }
}