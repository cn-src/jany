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

import cn.javaer.jany.util.TimeUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.jackson")
public class JacksonPlusProperties {

    private boolean enabled = true;

    private Format format = new Format();

    @Data
    public static class Format {

        /**
         * Date format to use, for example `yyyy-MM-dd`.
         */
        private String date = TimeUtils.DATE_PATTERN;

        /**
         * Time format to use, for example `HH:mm:ss`.
         */
        private String time = TimeUtils.TIME_PATTERN;

        /**
         * Date-time format to use, for example `yyyy-MM-dd HH:mm:ss`.
         */
        private String dateTime = TimeUtils.DATE_TIME_PATTERN;
    }
}