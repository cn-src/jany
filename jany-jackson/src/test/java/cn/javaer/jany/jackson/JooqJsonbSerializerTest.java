package cn.javaer.jany.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Data;
import org.jooq.JSONB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author cn-src
 */
class JooqJsonbSerializerTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(JooqJsonbSerializer.INSTANCE);
        this.mapper.registerModule(module);
    }

    @Test
    void serialize() throws Exception {
        final Demo demo = new Demo();
        demo.setStr("val");
        //language=JSON
        demo.setJsonb(JSONB.valueOf("{\"demo\":123}"));
        final String value = this.mapper.writeValueAsString(demo);
        JSONAssert.assertEquals("{\"str\":\"val\",\"jsonb\":{\"demo\":123}}", value, false);
    }

    @Test
    void serializeNull() throws Exception {
        final Demo demo = new Demo();
        demo.setStr("val");
        demo.setJsonb(JSONB.valueOf(null));
        final String value = this.mapper.writeValueAsString(demo);
        JSONAssert.assertEquals("{\"str\":\"val\",\"jsonb\":null}", value, false);
    }

    @Data
    static class Demo {
        String str;
        JSONB jsonb;
    }
}