package cn.javaer.jany.jackson;

import cn.javaer.jany.format.Desensitized;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author cn-src
 */
public class StringHandlerSerializer extends StdSerializer<String> implements ContextualSerializer {

    private static final long serialVersionUID = 4104322372036119909L;

    public static final StringHandlerSerializer INSTANCE = new StringHandlerSerializer(null);

    private final Desensitized desensitized;

    public StringHandlerSerializer(Desensitized desensitized) {
        super(String.class);
        this.desensitized = desensitized;
    }

    @Override
    public void serialize(final String str, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        if (null != desensitized) {
            jsonGenerator.writeString(desensitized.type().fn().apply(str));
            return;
        }
        jsonGenerator.writeString(str);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Desensitized de = property.getAnnotation(Desensitized.class);
        if (null == de) {
            return INSTANCE;
        }
        return new StringHandlerSerializer(de);
    }
}