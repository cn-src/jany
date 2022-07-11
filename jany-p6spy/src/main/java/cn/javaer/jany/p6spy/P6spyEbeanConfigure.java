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
            replaceUrl(config.getDataSourceConfig());
            replaceUrl(config.getReadOnlyDataSourceConfig());
        }
    }

    public void replaceUrl(DataSourceConfig ds) {
        if (ds != null && ds.getUrl() != null && !ds.getUrl().contains(":p6spy:")) {
            ds.setUrl(ds.getUrl().replaceFirst("jdbc:", "jdbc:p6spy:"));
        }
        if (ds != null && ds.getReadOnlyUrl() != null
            && !ds.getReadOnlyUrl().contains(":p6spy:")) {
            ds.setReadOnlyUrl(ds.getReadOnlyUrl().replaceFirst("jdbc:", "jdbc:p6spy:"));
        }
    }
}