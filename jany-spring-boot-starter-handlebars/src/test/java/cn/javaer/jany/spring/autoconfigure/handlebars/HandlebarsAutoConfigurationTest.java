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
import com.github.jknack.handlebars.Template;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class HandlebarsAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(HandlebarsAutoConfiguration.class))
        .withPropertyValues("jany.handlebars.prefix:classpath:/");

    @Test
    void basicAutoConfiguration() {
        this.contextRunner.run(context -> {
            final Handlebars handlebars = context.getBean(Handlebars.class);
            final Template template = handlebars.compile("test");
            final String out = template.apply(Maps.newHashMap("demo", "hi"));
            assertThat(out).isEqualTo("hi");
        });
    }
}