package cn.javaer.jany.spring.format;

import cn.javaer.jany.format.DateTimeFormat;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.datetime.standard.TemporalAccessorPrinter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * @author cn-src
 */
public class DateTimeFormatter extends EmbeddedValueResolutionSupport
    implements AnnotationFormatterFactory<DateTimeFormat> {

    @Override
    @NonNull
    public Set<Class<?>> getFieldTypes() {
        return Collections.singleton(LocalDateTime.class);
    }

    @Override
    @NonNull
    public Printer<?> getPrinter(final DateTimeFormat annotation,
                                 final @NotNull Class<?> fieldType) {
        return new TemporalAccessorPrinter(
            java.time.format.DateTimeFormatter.ofPattern(annotation.dateTimePattern()));
    }

    @Override
    @NonNull
    public Parser<?> getParser(@NonNull final DateTimeFormat annotation,
                               final @NotNull Class<?> fieldType) {
        return new DateTimeParser(annotation);
    }
}