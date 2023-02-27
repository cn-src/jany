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
import io.ebean.SqlRow;
import io.ebean.Transaction;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author cn-src
 */
class DbUtilsTest {

    private static EmbeddedPostgres pg;

    private static Database db;

    @BeforeAll
    static void beforeAll() throws IOException {
        pg = EmbeddedPostgres.start();
        db = EmbeddedPostgresUtils.create(pg);
        db.script().run("/DbUtilsTest.ddl");
    }

    @AfterAll
    static void afterAll() throws IOException {
        db.shutdown();
        pg.close();
    }

    @BeforeEach
    void setUp() {
        db.truncate("demo");
    }

    @Test
    void insert() {
        try (final Transaction tran = db.beginTransaction()) {
            DbUtils.insert(new InsertArgs().tableName("demo").rowList(Arrays.asList(
                Map.of("id", 1, "name", "name1", "created_date", LocalDateTime.now()),
                Map.of("id", 2, "name", "name2", "created_date", LocalDateTime.now()),
                Map.of("id", 3, "name", "name3", "created_date", LocalDateTime.now())
            )));
            tran.commit();
        }
        final List<SqlRow> rows = db.sqlQuery("SELECT * FROM demo").findList();
        Assertions.assertThat(rows).hasSize(3);
    }

    @Test
    void update() {
        try (final Transaction tran = db.beginTransaction()) {
            DbUtils.insert(new InsertArgs().tableName("demo").rowList(Arrays.asList(
                Map.of("id", 1, "name", "name1", "created_date", LocalDateTime.now()),
                Map.of("id", 2, "name", "name2", "created_date", LocalDateTime.now()),
                Map.of("id", 3, "name", "name3", "created_date", LocalDateTime.now())
            )));
            tran.commit();
        }
        try (final Transaction tran = db.beginTransaction()) {
            DbUtils.update(new UpdateArgs().tableName("demo").updateKey("id").rowList(Arrays.asList(
                Map.of("id", 1, "name", "name1_updated", "created_date", LocalDateTime.now()),
                Map.of("id", 2, "name", "name2_updated", "created_date", LocalDateTime.now()),
                Map.of("id", 3, "name", "name3_updated", "created_date", LocalDateTime.now())
            )));
            tran.commit();
        }
        final List<SqlRow> rows = db.sqlQuery("SELECT * FROM demo").findList();
        Assertions.assertThat(rows).hasSize(3);
        Assertions.assertThat(rows.get(0).getString("name")).isEqualTo("name1_updated");
        Assertions.assertThat(rows.get(1).getString("name")).isEqualTo("name2_updated");
        Assertions.assertThat(rows.get(2).getString("name")).isEqualTo("name3_updated");
    }

    @Test
    void upsert() {
        try (final Transaction tran = db.beginTransaction()) {
            DbUtils.insert(new InsertArgs().tableName("demo").rowList(Arrays.asList(
                Map.of("id", 1, "name", "name1", "created_date", LocalDateTime.now()),
                Map.of("id", 2, "name", "name2", "created_date", LocalDateTime.now()),
                Map.of("id", 3, "name", "name3", "created_date", LocalDateTime.now())
            )));
            tran.commit();
        }
        try (final Transaction tran = db.beginTransaction()) {
            DbUtils.upsert(new UpsertArgs().tableName("demo").upsertKey("id").mode(UpsertMode.UPDATE)
                .rowList(Arrays.asList(
                    Map.of("id", 1, "name", "name1_updated", "created_date", LocalDateTime.now()),
                    Map.of("id", 2, "name", "name2_updated", "created_date", LocalDateTime.now()),
                    Map.of("id", 3, "name", "name3_updated", "created_date", LocalDateTime.now()),
                    Map.of("id", 4, "name", "name4", "created_date", LocalDateTime.now())
                )));
            tran.commit();
        }
        final List<SqlRow> rows = db.sqlQuery("SELECT * FROM demo").findList();
        Assertions.assertThat(rows).hasSize(4);
        Assertions.assertThat(rows.get(0).getString("name")).isEqualTo("name1_updated");
        Assertions.assertThat(rows.get(1).getString("name")).isEqualTo("name2_updated");
        Assertions.assertThat(rows.get(2).getString("name")).isEqualTo("name3_updated");
        Assertions.assertThat(rows.get(3).getString("name")).isEqualTo("name4");
    }
}