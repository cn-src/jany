package cn.javaer.jany.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.tools.StringUtils;

import java.io.IOException;

/**
 * @author cn-src
 */
@Deprecated
public class JooqRecordSerializer extends StdSerializer<Record> {

    private static final long serialVersionUID = 2404332678897321768L;

    public static final JooqRecordSerializer INSTANCE = new JooqRecordSerializer();

    protected JooqRecordSerializer() {
        super(Record.class);
    }

    @Override
    public void serialize(final Record value, final JsonGenerator gen,
                          final SerializerProvider provider) throws IOException {
        final Field<?>[] fields = value.fields();
        gen.writeStartObject();
        for (final Field<?> field : fields) {
            gen.writeObjectField(StringUtils.toCamelCaseLC(field.getName()), value.get(field));
        }
        gen.writeEndObject();
    }
}