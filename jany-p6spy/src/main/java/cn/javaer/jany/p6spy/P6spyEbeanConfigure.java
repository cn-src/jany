package cn.javaer.jany.p6spy;

import com.p6spy.engine.spy.P6DataSource;
import io.ebean.config.AutoConfigure;
import io.ebean.config.DatabaseConfig;

/**
 * @author cn-src
 */
public class P6spyEbeanConfigure implements AutoConfigure {
    @Override
    public void preConfigure(DatabaseConfig config) {

    }

    @Override
    public void postConfigure(DatabaseConfig config) {
        if (!P6spyHelper.isEnabled()) {
            return;
        }
        P6spyHelper.initConfig();
        if (config.getDataSource() != null) {
            config.setDataSource(new P6DataSource(config.getDataSource()));
        }
        if (config.getReadOnlyDataSource() != null) {
            config.setReadOnlyDataSource(new P6DataSource(config.getReadOnlyDataSource()));
        }
    }
}