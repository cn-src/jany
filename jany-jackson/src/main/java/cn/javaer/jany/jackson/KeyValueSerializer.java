package cn.javaer.jany.jackson;

import cn.javaer.jany.model.KeyValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author cn-src
 */
@SuppressWarnings("rawtypes")
public class KeyValueSerializer extends StdSerializer<KeyValue> {

    public static final KeyValueSerializer INSTANCE = new KeyValueSerializer();

    protected KeyValueSerializer() {
        super(KeyValue.class);
    }

    @Override
    public void serialize(final KeyValue keyValue, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObjectField(keyValue.getKey(), keyValue.getValue());
    }
}