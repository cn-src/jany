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

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Handlebars.class)
@EnableConfigurationProperties(HandlebarsProperties.class)
@ConditionalOnProperty(prefix = "jany.handlebars", name = "enabled", havingValue = "true",
    matchIfMissing = true)
public class HandlebarsAutoConfiguration {

    private final HandlebarsProperties handlebars;

    public HandlebarsAutoConfiguration(final HandlebarsProperties handlebars) {
        this.handlebars = handlebars;
    }

    @Bean
    @ConditionalOnMissingBean
    public Handlebars handlebars(final TemplateLoader templateLoader) {
        return new Handlebars(templateLoader);
    }

    @Bean
    @ConditionalOnMissingBean(TemplateLoader.class)
    public HandlebarsResourceTemplateLoader handlebarsTemplateLoader() {
        final HandlebarsResourceTemplateLoader loader = new HandlebarsResourceTemplateLoader();
        loader.setPrefix(this.handlebars.getPrefix());
        loader.setSuffix(this.handlebars.getSuffix());
        loader.setCharset(this.handlebars.getCharset());
        return loader;
    }
}