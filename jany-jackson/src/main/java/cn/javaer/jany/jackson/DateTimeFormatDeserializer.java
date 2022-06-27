package cn.javaer.jany.jackson;

import cn.javaer.jany.format.DateMaxTime;
import cn.javaer.jany.format.DateMinTime;
import cn.javaer.jany.format.DateTimeFormat;
import cn.javaer.jany.util.AnnotationUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * @author cn-src
 */
public class DateTimeFormatDeserializer extends JsonDeserializer<LocalDateTime> implements ContextualDeserializer {

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
        final DateTimeFormat format = AnnotationUtils.findMergedAnnotation(DateTimeFormat.class,
            property.getAnnotation(DateTimeFormat.class),
            property.getAnnotation(DateMinTime.class),
            property.getAnnotation(DateMaxTime.class)
        );
        return Optional.ofNullable(format).map(DateTimeFormatDeserializer::new).orElse(null);
    }
}