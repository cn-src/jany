package cn.javaer.jany.jackson;

import cn.javaer.jany.format.DateMaxTime;
import cn.javaer.jany.format.DateMinTime;
import cn.javaer.jany.format.DateTimeFormat;
import cn.javaer.jany.format.Desensitized;
import cn.javaer.jany.util.AnnotationUtils;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.Annotated;

import java.util.Optional;

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
    public Object findDeserializer(final Annotated a) {
        final DateTimeFormat format = AnnotationUtils.findMergedAnnotation(DateTimeFormat.class,
            a.getAnnotation(DateTimeFormat.class),
            a.getAnnotation(DateMinTime.class),
            a.getAnnotation(DateMaxTime.class)
        );
        return Optional.ofNullable(format)
            .map(it -> (Object) new DateTimeFormatDeserializer(it))
            .orElse(super.findDeserializer(a));
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