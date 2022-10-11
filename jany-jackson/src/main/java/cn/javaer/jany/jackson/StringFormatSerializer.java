package cn.javaer.jany.jackson;

import cn.hutool.core.lang.Opt;
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
public class StringFormatSerializer extends StdSerializer<String> implements ContextualSerializer {

    private static final long serialVersionUID = 4104322372036119909L;

    public static final StringFormatSerializer INSTANCE = new StringFormatSerializer(null, null);

    private final Desensitized desensitized;

    private final StringFormat stringFormat;

    public StringFormatSerializer(Desensitized desensitized, StringFormat stringFormat) {
        super(String.class);
        this.desensitized = desensitized;
        this.stringFormat = stringFormat;
    }

    @Override
    public void serialize(final String str, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        String value = StringFormat.Formatter.format(str, stringFormat);
        if (null == value) {
            jsonGenerator.writeNull();
            return;
        }
        if (null != desensitized) {
            value = desensitized.type().fn().apply(value);
        }
        jsonGenerator.writeString(value);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        Iterable<Annotation> annotations = property.getMember().getAllAnnotations().annotations();
        Opt<Desensitized> deOpt = AnnotationUtils.findMergedAnnotation(Desensitized.class, annotations);
        Opt<StringFormat> sfOpt = AnnotationUtils.findMergedAnnotation(StringFormat.class, annotations);
        Opt<StringFormat> sfClazzOpt = AnnotationUtils.findMergedAnnotation(StringFormat.class, property.getMember().getDeclaringClass());
        return new StringFormatSerializer(deOpt.orElse(null), sfOpt.orElse(sfClazzOpt.orElse(null)));
    }
}