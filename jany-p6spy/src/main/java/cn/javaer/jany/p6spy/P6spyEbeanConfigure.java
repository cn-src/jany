package cn.javaer.jany.p6spy;

import io.ebean.config.AutoConfigure;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;

/**
 * @author cn-src
 */
public class P6spyEbeanConfigure implements AutoConfigure {
    @Override
    public void preConfigure(DatabaseConfig config) {

    }

    @Override
    public void postConfigure(DatabaseConfig config) {
        final boolean ebeanEnabled = "true".equalsIgnoreCase(
            config.getProperties().getProperty("ebean.p6spy.enabled"));
        final boolean janyOnlyEnabled =
            !config.getProperties().containsKey("ebean.p6spy.enabled") &&
                "true".equalsIgnoreCase(config.getProperties().getProperty("jany.p6spy.enabled"));

        if (ebeanEnabled || janyOnlyEnabled) {
            P6spyHelper.initConfig();
            final DataSourceConfig ds = config.getDataSourceConfig();
            if (ds != null && ds.getDriver() != null && !ds.getDriver().contains(":p6spy:")) {
                ds.setDriver(ds.getDriver().replaceFirst("jdbc:", "jdbc:p6spy:"));
            }
            final DataSourceConfig rds = config.getReadOnlyDataSourceConfig();
            if (rds != null && rds.getDriver() != null && !rds.getDriver().contains(":p6spy:")) {
                rds.setDriver(rds.getDriver().replaceFirst("jdbc:", "jdbc:p6spy:"));
            }
        }
    }
}