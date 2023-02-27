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

import cn.hutool.core.lang.Assert;
import io.ebean.DB;
import io.ebean.Database;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cn-src
 */
@Data
@Accessors(fluent = true)
public class InsertArgs {
    private Database db;

    private List<Map<String, Object>> rowList;

    private String tableName;

    private Set<String> columns;

    public Database db() {
        return db == null ? DB.getDefault() : db;
    }

    public InsertArgs rowList(List<Map<String, Object>> rowList) {
        Assert.notEmpty(rowList);
        this.rowList = rowList;
        this.columns = rowList.get(0).keySet();
        return this;
    }
}