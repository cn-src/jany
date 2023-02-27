/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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