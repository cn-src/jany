package cn.javaer.jany.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.jooq.JSONB;

import java.io.IOException;

/**
 * @author cn-src
 */
@Deprecated
public class JooqJsonbSerializer extends StdSerializer<JSONB> {

    public static final JooqJsonbSerializer INSTANCE = new JooqJsonbSerializer();
    private static final long serialVersionUID = 141237569175467425L;

    protected JooqJsonbSerializer() {
        super(JSONB.class);
    }

    @Override
    public void serialize(final JSONB jsonb, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeRawValue(jsonb.data());
    }
}