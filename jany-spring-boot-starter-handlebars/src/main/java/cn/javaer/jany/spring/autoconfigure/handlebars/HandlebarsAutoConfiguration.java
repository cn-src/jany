package cn.javaer.jany.spring.autoconfigure.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Handlebars.class)
@EnableConfigurationProperties(HandlebarsProperties.class)
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