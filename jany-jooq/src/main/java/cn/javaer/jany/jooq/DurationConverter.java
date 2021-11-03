package cn.javaer.jany.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

import java.time.Duration;

/**
 * @author cn-src
 */
public enum DurationConverter implements Converter<String, Duration> {

    /**
     * 单实例
     */
    INSTANCE;

    private static final long serialVersionUID = 599360862926272439L;

    @Override
    public Duration from(final String db) {

        return null == db ? null : Duration.parse(db);
    }

    @Override
    public String to(final Duration duration) {
        if (null == duration) {
            return null;
        }

        return duration.toString();
    }

    @Override
    public @NotNull Class<String> fromType() {
        return String.class;
    }

    @Override
    public @NotNull Class<Duration> toType() {
        return Duration.class;
    }
}