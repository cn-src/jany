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