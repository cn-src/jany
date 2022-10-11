package cn.javaer.jany.jackson;

import cn.javaer.jany.format.DateTimeFormat;
import cn.javaer.jany.format.Desensitized;
import cn.javaer.jany.util.AnnotationUtils;
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

        if (AnnotationUtils.hasMergedAnnotation(DateTimeFormat.class, ann.annotations())) {
            return DateTimeFormatDeserializer.INSTANCE;
        }
        return null;
    }

    @Override
    public Object findSerializer(Annotated ann) {
        if (AnnotationUtils.hasMergedAnnotation(Desensitized.class, ann.annotations())) {
            return StringHandlerSerializer.INSTANCE;
        }
        return null;
    }
}