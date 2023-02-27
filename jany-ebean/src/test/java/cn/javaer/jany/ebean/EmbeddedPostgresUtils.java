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