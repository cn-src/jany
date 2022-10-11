package cn.javaer.jany.jackson;

import cn.javaer.jany.format.DateTimeFormat;
import cn.javaer.jany.format.Desensitized;
import cn.javaer.jany.format.StringFormat;
import cn.javaer.jany.util.AnnotationUtils;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.lang.annotation.Annotation;

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
        if (ann instanceof AnnotatedMember) {
            AnnotatedMember annM = (AnnotatedMember) ann;
            Iterable<Annotation> annotations = annM.getAllAnnotations().annotations();

            if (AnnotationUtils.hasMergedAnnotation(DateTimeFormat.class, annotations)) {
                return DateTimeFormatDeserializer.INSTANCE;
            }
        }

        return super.findDeserializer(ann);
    }

    @Override
    public Object findSerializer(Annotated ann) {
        if (ann instanceof AnnotatedMember) {
            AnnotatedMember annM = (AnnotatedMember) ann;
            Iterable<Annotation> annotations = annM.getAllAnnotations().annotations();

            if (String.class.equals(annM.getRawType())) {
                if (AnnotationUtils.hasMergedAnnotation(Desensitized.class, annotations)) {
                    return StringFormatSerializer.INSTANCE;
                }
                if (AnnotationUtils.hasMergedAnnotation(StringFormat.class, annotations) ||
                    AnnotationUtils.hasMergedAnnotation(StringFormat.class, annM.getDeclaringClass())) {
                    return StringFormatSerializer.INSTANCE;
                }
            }
        }
        return super.findSerializer(ann);
    }
}