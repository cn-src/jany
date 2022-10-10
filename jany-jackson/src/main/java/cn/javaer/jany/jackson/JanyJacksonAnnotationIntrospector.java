package cn.javaer.jany.jackson;

import cn.javaer.jany.format.DateMaxTime;
import cn.javaer.jany.format.DateMinTime;
import cn.javaer.jany.format.DateTimeFormat;
import cn.javaer.jany.format.Desensitized;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.Annotated;

/**
 * @author cn-src
 */
public class JanyJacksonAnnotationIntrospector extends AnnotationIntrospector {
    private static final long serialVersionUID = -6156647757687961666L;

    public static final JanyJacksonAnnotationIntrospector INSTANCE = new JanyJacksonAnnotationIntrospector();

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public Object findDeserializer(final Annotated ann) {
        if (ann.getAnnotation(DateTimeFormat.class) != null
            || ann.getAnnotation(DateMinTime.class) != null
            || ann.getAnnotation(DateMaxTime.class) != null) {
            return DateTimeFormatDeserializer.INSTANCE;
        }
        return null;
    }

    @Override
    public Object findSerializer(Annotated ann) {
        final var de = _findAnnotation(ann, Desensitized.class);
        if (de != null) {
            return StringHandlerSerializer.INSTANCE;
        }
        return null;
    }
}