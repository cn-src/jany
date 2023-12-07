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

package cn.javaer.jany.p6spy;

import io.ebean.DatabaseBuilder;
import io.ebean.config.AutoConfigure;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;

import java.util.Properties;

/**
 * @author cn-src
 */
public class P6spyEbeanConfigure implements AutoConfigure {
    @Override
    public void preConfigure(DatabaseBuilder config) {

    }

    @Override
    public void postConfigure(DatabaseBuilder config) {
        final DatabaseBuilder.Settings settings = config.settings();
        final Properties props = settings.getProperties();

        final boolean ebeanEnabled = "true".equalsIgnoreCase(
                props.getProperty("ebean.p6spy.enabled"));
        final boolean janyOnlyEnabled =
                !props.containsKey("ebean.p6spy.enabled") &&
                        "true".equalsIgnoreCase(props.getProperty("jany.p6spy.enabled"));

        if (ebeanEnabled || janyOnlyEnabled) {
            P6spyHelper.initConfig();
            replaceUrl(settings.getDataSourceConfig());
            replaceUrl(settings.getReadOnlyDataSourceConfig());
        }
    }

    public void replaceUrl(
            io.ebean.datasource.DataSourceBuilder.Settings ds) {

        if (ds != null && ds.getUrl() != null && !ds.getUrl().contains(":p6spy:")) {
            ds.url(ds.getUrl().replaceFirst("jdbc:", "jdbc:p6spy:"));
        }
        if (ds != null && ds.getReadOnlyUrl() != null
                && !ds.getReadOnlyUrl().contains(":p6spy:")) {
            ds.readOnlyUrl(ds.getReadOnlyUrl().replaceFirst("jdbc:", "jdbc:p6spy:"));
        }
    }
}