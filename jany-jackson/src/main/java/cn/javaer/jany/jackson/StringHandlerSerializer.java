package cn.javaer.jany.jackson;

import cn.javaer.jany.exception.MissingAnnotationException;
import cn.javaer.jany.format.Desensitized;
import cn.javaer.jany.format.StringFormat;
import cn.javaer.jany.util.AnnotationUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * @author cn-src
 */
public class StringHandlerSerializer extends StdSerializer<String> implements ContextualSerializer {

    private static final long serialVersionUID = 4104322372036119909L;

    public static final StringHandlerSerializer INSTANCE = new StringHandlerSerializer(null, null);

    private final Desensitized desensitized;

    private final StringFormat stringFormat;

    public StringHandlerSerializer(Desensitized desensitized, StringFormat stringFormat) {
        super(String.class);
        this.desensitized = desensitized;
        this.stringFormat = stringFormat;
    }

    @Override
    public void serialize(final String str, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        String value = str;
        if (null != stringFormat && stringFormat.apply()) {
            if (stringFormat.trim()) {
                value = value.trim();
            }
            if (stringFormat.emptyToNull() && value.isEmpty()) {
                jsonGenerator.writeNull();
                return;
            }
        }
        if (null != desensitized) {
            value = desensitized.type().fn().apply(value);
        }
        jsonGenerator.writeString(value);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        Iterable<Annotation> annotations = property.getMember().getAllAnnotations().annotations();
        return AnnotationUtils.findMergedAnnotation(Desensitized.class, annotations)
            .map(it -> new StringHandlerSerializer(it, stringFormat))
            .orElseThrow(() -> new MissingAnnotationException(Desensitized.class));
    }
}