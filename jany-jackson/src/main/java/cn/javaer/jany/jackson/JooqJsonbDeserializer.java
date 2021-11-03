package cn.javaer.jany.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.jooq.JSONB;

import java.io.IOException;

/**
 * @author cn-src
 */
public class JooqJsonbDeserializer extends StdDeserializer<JSONB> {

    public static final JooqJsonbDeserializer INSTANCE = new JooqJsonbDeserializer();
    private static final long serialVersionUID = 141237569175467425L;

    protected JooqJsonbDeserializer() {
        super(JSONB.class);
    }

    @Override
    public JSONB deserialize(final JsonParser parser, final DeserializationContext cont) throws IOException {
        final TreeNode treeNode = parser.readValueAsTree();
        return JSONB.valueOf(treeNode.toString());
    }
}