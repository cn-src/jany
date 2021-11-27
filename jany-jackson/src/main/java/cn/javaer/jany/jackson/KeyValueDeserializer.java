package cn.javaer.jany.jackson;

import cn.javaer.jany.model.KeyValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author cn-src
 */
@SuppressWarnings("rawtypes")
public class KeyValueDeserializer extends StdDeserializer<KeyValue> {

    public static final KeyValueDeserializer INSTANCE = new KeyValueDeserializer();

    protected KeyValueDeserializer() {
        super(KeyValue.class);
    }

    @Override
    public KeyValue<?> deserialize(final JsonParser parser, final DeserializationContext cont) throws IOException {
        final TreeNode treeNode = parser.readValueAsTree();
        if (!treeNode.isObject()) {
            throw new IllegalArgumentException("The value must be an object.");
        }
        if (treeNode.size() > 1) {
            throw new IllegalArgumentException("KeyValue must have only one element.");
        }
        if (!treeNode.fieldNames().hasNext()) {
            return KeyValue.EMPTY;
        }
        final String key = treeNode.fieldNames().next();
        return KeyValue.of(key, treeNode.get(key).traverse().currentValue());
    }
}