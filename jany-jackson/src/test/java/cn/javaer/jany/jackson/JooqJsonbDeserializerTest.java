package cn.javaer.jany.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Data;
import org.jooq.JSONB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class JooqJsonbDeserializerTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        final SimpleModule module = new SimpleModule();
        module.addDeserializer(JSONB.class, JooqJsonbDeserializer.INSTANCE);
        this.mapper.registerModule(module);
    }

    @Test
    void deserialize() throws Exception {
        //language=JSON
        final Demo demo = this.mapper.readValue(
            "{\"str\": \"str1\", \"jsonb\": {\"data\": \"demo1\"}}",
            Demo.class);
        assertThat(demo).extracting(Demo::getStr).isEqualTo("str1");
        assertThat(demo.jsonb.data()).isEqualTo("{\"data\":\"demo1\"}");
    }

    @Test
    void deserializeNull() throws Exception {
        //language=JSON
        final Demo demo = this.mapper.readValue(
            "{\"str\": \"str1\"}",
            Demo.class);
        assertThat(demo).extracting(Demo::getStr).isEqualTo("str1");
        assertThat(demo.jsonb).isNull();
    }

    @Data
    static class Demo {
        String str;
        JSONB jsonb;
    }
}