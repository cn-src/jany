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

import cn.javaer.jany.model.Page;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.model.Sort;
import cn.javaer.jany.test.JsonAssert;
import cn.javaer.jany.test.Log;
import io.ebean.DB;
import io.ebean.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class BaseFinderTest {
    Database db = DB.getDefault();

    @BeforeEach
    void setUp() {
        db.truncate("DEMO");
    }

    @Test
    void allSort() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final List<Demo> demos = Demo.find.list(Sort.DEFAULT);
        JsonAssert.assertEqualsAndOrder("BaseFinderTest.allSort.out.json", Log.json(demos));
    }

    @Test
    void page() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final Page<Demo> demos = Demo.find.page(PageParam.of(1, 2));
        JsonAssert.assertEqualsAndOrder("BaseFinderTest.pagedSort.out.json", Log.json(demos));
    }

    @Test
    void pageExample() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final Page<Demo> demos = Demo.find.page(new DemoExample().setName("name2"),
            PageParam.of(1, 2));
        assertThat(demos.getTotal()).isEqualTo(1);
        assertThat(demos.getContent().get(0).getName()).isEqualTo("name2");
    }
}