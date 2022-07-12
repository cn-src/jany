package cn.javaer.jany.ebean;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.zonky.test.db.postgres.junit5.SingleInstancePostgresExtension;

/**
 * @author cn-src
 */
public class EmbeddedPostgresUtils {
    public static Database create(SingleInstancePostgresExtension pg) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setPassword("postgres");
        dataSourceConfig.setUrl(pg.getEmbeddedPostgres().getJdbcUrl("postgres", "postgres"));
        DatabaseConfig config = new DatabaseConfig();
        config.setDataSourceConfig(dataSourceConfig);
        return DatabaseFactory.create(config);
    }
}