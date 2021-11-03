package cn.javaer.jany.spring.format;

import cn.javaer.jany.format.DateTimeFormat;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Parser;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author cn-src
 */
public final class DateTimeParser implements Parser<LocalDateTime> {
    private final DateTimeFormat annotation;

    public DateTimeParser(final DateTimeFormat annotation) {
        this.annotation = annotation;
    }

    @Override
    public @NotNull LocalDateTime parse(final String text, @NonNull final Locale locale) {
        if (this.annotation.datePattern().length() == text.length()) {
            final LocalDate date = LocalDate.parse(text,
                DateTimeFormatter.ofPattern(this.annotation.datePattern(), locale));
            return DateTimeFormat.Conversion.conversion(date, this.annotation);
        }
        return LocalDateTime.parse(text,
            DateTimeFormatter.ofPattern(this.annotation.dateTimePattern(), locale));
    }
}