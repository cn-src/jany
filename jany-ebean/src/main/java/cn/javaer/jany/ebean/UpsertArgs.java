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

import io.ebean.DB;
import io.ebean.Database;
import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.hutool.core.lang.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cn-src
 */
@Data
@Accessors(fluent = true)
public class UpsertArgs {
    private Database db;

    private List<Map<String, Object>> rowList;

    private String tableName;

    private Set<String> insertColumns;

    private Set<String> updateColumns;

    private Set<String> upsertKeys = new HashSet<>();

    private UpsertMode mode;

    public Database db() {
        return db == null ? DB.getDefault() : db;
    }

    public UpsertArgs rowList(List<Map<String, Object>> rowList) {
        Assert.notEmpty(rowList);
        this.rowList = rowList;
        final Set<String> columns = rowList.get(0).keySet();
        this.insertColumns = columns;
        this.updateColumns = columns;
        return this;
    }

    public UpsertArgs addUpsertKey(String key) {
        this.upsertKeys.add(key);
        return this;
    }
}