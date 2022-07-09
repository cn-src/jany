package cn.javaer.jany.spring.autoconfigure.p6spy;

import cn.javaer.jany.p6spy.BeautifulFormat;
import cn.javaer.jany.p6spy.TimestampJdbcEventListener;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.p6spy.engine.common.Loggable;
import com.p6spy.engine.spy.option.EnvironmentVariables;
import com.p6spy.engine.spy.option.P6OptionsSource;
import com.p6spy.engine.spy.option.SpyDotProperties;
import com.p6spy.engine.spy.option.SystemProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SpringDoc 支持.
 *
 * @author cn-src
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Loggable.class, BeautifulFormat.class, TimestampJdbcEventListener.class})
@AutoConfigureBefore({DataSourceDecoratorAutoConfiguration.class})
@ConditionalOnProperty(prefix = "jany.p6spy", name = "enabled", havingValue = "true",
    matchIfMissing = true)
@EnableConfigurationProperties(P6spyProperties.class)
public class P6spyAutoConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        SpyDotProperties spyDotProperties = null;
        try {
            spyDotProperties = new SpyDotProperties();
        }
        catch (final IOException ignored) {
        }
        final Map<String, String> props = Stream.of(spyDotProperties,
                new EnvironmentVariables(), new SystemProperties())
            .filter(Objects::nonNull)
            .map(P6OptionsSource::getOptions)
            .filter(Objects::nonNull)
            .flatMap(options -> options.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                // always using value from the first P6OptionsSource
                (value1, value2) -> value1));
        //noinspection AlibabaUndefineMagicConstant
        if (!props.containsKey("logMessageFormat")) {
            System.setProperty("p6spy.config.logMessageFormat",
                BeautifulFormat.class.getName());
            System.setProperty("p6spy.config.customLogMessageFormat",
                "time %(executionTime) ms | url %(url)\n%(sql)");
        }
    }
}