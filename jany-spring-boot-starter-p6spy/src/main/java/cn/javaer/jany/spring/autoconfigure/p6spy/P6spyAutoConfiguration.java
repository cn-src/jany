package cn.javaer.jany.spring.autoconfigure.p6spy;

import cn.javaer.jany.p6spy.BeautifulFormat;
import cn.javaer.jany.p6spy.P6spyHelper;
import cn.javaer.jany.p6spy.TimestampJdbcEventListener;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.p6spy.engine.common.Loggable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
        P6spyHelper.initConfig();
    }
}