package cn.javaer.jany.jackson;

import cn.javaer.jany.format.DateTimeFormat;
import cn.javaer.jany.util.AnnotationUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author cn-src
 */
public class DateTimeFormatDeserializer extends JsonDeserializer<LocalDateTime> implements ContextualDeserializer {

    public static final DateTimeFormatDeserializer INSTANCE = new DateTimeFormatDeserializer();

    private final DateTimeFormat dateTimeFormat;

    @SuppressWarnings("unused")
    protected DateTimeFormatDeserializer() {
        this.dateTimeFormat = null;
    }

    public DateTimeFormatDeserializer(final DateTimeFormat dateTimeFormat) {
        Objects.requireNonNull(dateTimeFormat);
        this.dateTimeFormat = dateTimeFormat;
    }

    @Override
    public LocalDateTime deserialize(final JsonParser parser,
                                     final DeserializationContext context) throws IOException {
        Objects.requireNonNull(this.dateTimeFormat);
        if (this.dateTimeFormat.datePattern().length() == parser.getText().length()) {
            final LocalDate date = LocalDate.parse(parser.getText(),
                DateTimeFormatter.ofPattern(this.dateTimeFormat.datePattern()));
            return DateTimeFormat.Conversion.conversion(date, this.dateTimeFormat);
        }
        return LocalDateTime.parse(parser.getText(),
            DateTimeFormatter.ofPattern(this.dateTimeFormat.dateTimePattern()));
    }

    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext context,
                                                final BeanProperty property) {
        Iterable<Annotation> annotations = property.getMember().getAllAnnotations().annotations();
        return AnnotationUtils.findMergedAnnotation(DateTimeFormat.class, annotations)
            .map(DateTimeFormatDeserializer::new)
            .orElse(null);
    }
}