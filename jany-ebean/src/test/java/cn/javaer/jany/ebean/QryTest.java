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

import cn.javaer.jany.ebean.query.QDemo;
import io.ebean.DB;
import io.ebean.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class QryTest {

    Database db = DB.byName("db");

    @BeforeEach
    void setUp() {
        db.truncate("DEMO");
    }

    @Test
    void build() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final QDemo qDemo = new QDemo(DB.byName("db"));
        final List<Demo> list = Qry.of(qDemo).opt(qDemo.id::eq, 1L).list();
        assertThat(list).hasSize(1);

        final List<Demo> list2 = Qry.of(qDemo)
            .opt(qDemo.id::eq, 1L)
            .opt(qDemo.createdDate::between, LocalDateTime.now(), LocalDateTime.now())
            .list();
        assertThat(list2).isEmpty();
    }

    @Test
    void orTest() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final QDemo q = new QDemo(DB.byName("db"));
        final List<Demo> list = Qry.of(q)
            .fn(q::or)
            .opt(q.id::eq, 1L)
            .opt(q.id::eq, (Long) null)
            .fn(q::endOr)
            .list();
        assertThat(list).hasSize(1);
    }

    @Test
    void orTest2() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final QDemo q = new QDemo(DB.byName("db"));
        final List<Demo> list = Qry.of(q)
            .fn(() -> q.where().or().query())
            .opt(q.id::eq, 1L)
            .opt(q.id::eq, (Long) null)
            .list();
        assertThat(list).hasSize(1);
    }
}