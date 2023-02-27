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

package cn.javaer.jany.spring.autoconfigure.handlebars;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author cn-src
 */
@Data
@ConfigurationProperties(prefix = "jany.handlebars")
public class HandlebarsProperties {

    private boolean enabled = true;

    public static final String DEFAULT_PREFIX = "file:"
        + System.getProperty("user.dir") + "/conf/templates/";

    public static final String DEFAULT_SUFFIX = ".hbs";

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private String prefix = DEFAULT_PREFIX;

    private String suffix = DEFAULT_SUFFIX;

    private Charset charset = DEFAULT_CHARSET;
}