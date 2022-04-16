package cn.javaer.jany.jackson;

import cn.javaer.jany.model.KeyValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonToken.VALUE_NULL;

/**
 * @author cn-src
 */
public class KeyValueDeserializer extends StdDeserializer<KeyValue<?>> implements ContextualDeserializer {

    public static final KeyValueDeserializer INSTANCE = new KeyValueDeserializer();

    public KeyValueDeserializer() {
        super(KeyValue.class);
    }

    public KeyValueDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public KeyValue<?> deserialize(final JsonParser parser, final DeserializationContext cont) throws IOException {
        final JsonToken token = parser.currentToken();
        if (token != JsonToken.START_OBJECT) {
            cont.handleUnexpectedToken(KeyValue.class, parser);
        }
        String key = parser.nextFieldName();
        if (key == null) {
            return KeyValue.EMPTY;
        }
        final JavaType javaType = _valueType.containedTypeOrUnknown(0);
        final JsonDeserializer<Object> deserializer = cont.findRootValueDeserializer(javaType);
        JsonToken jsonToken = parser.nextToken();
        Object value = (jsonToken != VALUE_NULL) ? deserializer.deserialize(parser, cont) :
            deserializer.getNullValue(cont);
        if (null != parser.nextFieldName()) {
            cont.handleUnexpectedToken(KeyValue.class, parser);
        }
        return KeyValue.of(key, value);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context,
                                                BeanProperty property) {

        return new KeyValueDeserializer(context.getContextualType());
    }
}