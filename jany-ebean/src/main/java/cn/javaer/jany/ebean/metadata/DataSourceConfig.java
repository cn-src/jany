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

package cn.javaer.jany.ebean.metadata;

import lombok.Data;

import java.sql.Connection;
import java.sql.Driver;
import java.util.List;
import java.util.Map;

/**
 * 用于生成 Ebean 配置的 SpringBoot 元数据信息，仅用于生成便于 IDE 提示，并不实际与 SpringBoot 集成。
 *
 * @author cn-src
 */
@Data
class DataSourceConfig {

    private String readOnlyUrl;

    private String url;

    /**
     * 数据源用户名
     */
    private String username;

    /**
     * 数据源密码
     */
    private String password;

    /**
     * 数据源 schema
     */
    private String schema;

    /**
     * 数据源平台
     */
    private String platform;

    private String ownerUsername;

    private String ownerPassword;

    /**
     * 数据源驱动
     */
    private Class<? extends Driver> driver;

    private int minConnections = 2;

    private int maxConnections = 200;

    private int isolationLevel = Connection.TRANSACTION_READ_COMMITTED;

    private boolean autoCommit;

    private boolean readOnly;

    private String heartbeatSql;

    private int heartbeatTimeoutSeconds = 3;

    private boolean captureStackTrace;

    private int maxStackTraceSize = 5;

    private int leakTimeMinutes = 30;

    private int maxInactiveTimeSecs = 300;

    private int maxAgeMinutes = 0;

    private int trimPoolFreqSecs = 59;

    private int pstmtCacheSize = 50;

    private int cstmtCacheSize = 20;

    /**
     * io.ebean.datasource.DataSourceConfig#waitTimeoutMillis
     */
    private int waitTimeout = 1000;

    private String poolListener;

    private boolean offline;

    private boolean failOnStart = true;

    private Map<String, String> customProperties;

    private List<String> initSql;
}