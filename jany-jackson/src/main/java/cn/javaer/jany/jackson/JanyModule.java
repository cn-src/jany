package cn.javaer.jany.jackson;

import cn.javaer.jany.util.TimeUtils;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class JanyModule extends SimpleModule {
    public JanyModule() {
        // @formatter:off
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(TimeUtils.DATE_TIME_FORMATTER));
        addSerializer(LocalDate.class, new LocalDateSerializer(TimeUtils.DATE_FORMATTER));
        addSerializer(LocalTime.class, new LocalTimeSerializer(TimeUtils.TIME_FORMATTER));

        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(TimeUtils.DATE_TIME_FORMATTER));
        addDeserializer(LocalDate.class, new LocalDateDeserializer(TimeUtils.DATE_FORMATTER));
        addDeserializer(LocalTime.class, new LocalTimeDeserializer(TimeUtils.TIME_FORMATTER));
        // @formatter:on
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.appendAnnotationIntrospector(JanyJacksonAnnotationIntrospector.INSTANCE);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public String getModuleName() {
        return "JanyModule";
    }
}