package cn.javaer.jany.exception;

import java.lang.annotation.Annotation;

public class MissingAnnotationException extends RuntimeException {
    public MissingAnnotationException(Class<? extends Annotation> clazz) {
        super("Missing annotation, type: " + clazz);
    }
}