package cn.javaer.jany.jackson;

import cn.javaer.jany.format.DateTimeFormat;
import cn.javaer.jany.format.Desensitized;
import cn.javaer.jany.format.StringFormat;
import cn.javaer.jany.util.AnnotationUtils;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author cn-src
 */
public class JanyJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {
    private static final long serialVersionUID = -6156647757687961666L;

    public static final JanyJacksonAnnotationIntrospector INSTANCE = new JanyJacksonAnnotationIntrospector();

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public Object findDeserializer(final Annotated ann) {
        if (AnnotationUtils.hasMergedAnnotation(DateTimeFormat.class, annotations(ann))) {
            return DateTimeFormatDeserializer.INSTANCE;
        }
        return super.findDeserializer(ann);
    }

    @Override
    public Object findSerializer(Annotated ann) {
        if (ann instanceof AnnotatedClass) {
            return super.findSerializer(ann);
        }
        Iterable<Annotation> annotations = annotations(ann);
        if (AnnotationUtils.hasMergedAnnotation(Desensitized.class, annotations) ||
            AnnotationUtils.hasMergedAnnotation(StringFormat.class, annotations)) {
            return StringFormatSerializer.INSTANCE;
        }
        return super.findSerializer(ann);
    }

    private Iterable<Annotation> annotations(Annotated ann) {
        if (ann instanceof AnnotatedMember) {
            return ((AnnotatedMember) ann).getAllAnnotations().annotations();
        }
        return Arrays.asList(ann.getAnnotated().getAnnotations());
    }
}