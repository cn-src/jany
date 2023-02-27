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

package cn.javaer.jany.spring.autoconfigure.jackson;

import cn.javaer.jany.jackson.JanyJacksonAnnotationIntrospector;
import cn.javaer.jany.jackson.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ObjectMapper.class, Json.class})
@AutoConfigureBefore(JacksonAutoConfiguration.class)
@ConditionalOnProperty(prefix = "jany.jackson", name = "enabled", havingValue = "true",
    matchIfMissing = true)
@EnableConfigurationProperties(JacksonPlusProperties.class)
public class JacksonPlusAutoConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer janyJacksonCustomizer(final JacksonPlusProperties jacksonPlusProperties) {
        return it -> {
            it.annotationIntrospector(JanyJacksonAnnotationIntrospector.INSTANCE);

            final DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(jacksonPlusProperties.getFormat().getDateTime());
            final DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern(jacksonPlusProperties.getFormat().getDate());
            final DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern(jacksonPlusProperties.getFormat().getTime());

            it.deserializerByType(LocalDateTime.class,
                new LocalDateTimeDeserializer(dateTimeFormatter));
            it.deserializerByType(LocalDate.class,
                new LocalDateDeserializer(dateFormatter));
            it.deserializerByType(LocalTime.class,
                new LocalTimeDeserializer(timeFormatter));

            it.serializerByType(LocalDateTime.class,
                new LocalDateTimeSerializer(dateTimeFormatter));
            it.serializerByType(LocalDate.class,
                new LocalDateSerializer(dateFormatter));
            it.serializerByType(LocalTime.class,
                new LocalTimeSerializer(timeFormatter));
        };
    }

    @Bean
    @Primary
    @ConditionalOnBean(ObjectMapper.class)
    @ConditionalOnMissingBean(Json.class)
    public Json json(final ObjectMapper objectMapper) {
        return new Json(objectMapper);
    }
}