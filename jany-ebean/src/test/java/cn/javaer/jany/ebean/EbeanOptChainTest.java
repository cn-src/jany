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
import cn.javaer.jany.util.OptChain;
import io.ebean.DB;
import org.junit.jupiter.api.Test;

/**
 * @author cn-src
 */
public class EbeanOptChainTest {
    @Test
    void testQBean() {
        OptChain.of(new QDemo(DB.byName("db")))
            .opt(q -> q.id::eq, 1)
            .opt(q -> q.id::between, 1, 4)
            .fn(QDemo::or)
            .opt(q -> q.name::eq, "name1")
            .opt(q -> q.name::eq, "name2")
            .fn(QDemo::endOr)
            .root().findList();
    }
}