package cn.javaer.jany.ebean;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;

/**
 * @author cn-src
 */
public class EmbeddedPostgresUtils {
    public static Database create(EmbeddedPostgres pg) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setPassword("postgres");
        dataSourceConfig.setUrl(pg.getJdbcUrl("postgres", "postgres"));
        DatabaseConfig config = new DatabaseConfig();
        config.setName("pg");
        config.setDataSourceConfig(dataSourceConfig);
        return DatabaseFactory.create(config);
    }
}