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
 *
 */

package cn.javaer.jany.util;

import cn.javaer.jany.jackson.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author cn-src
 */
class MergeUtilsTest {

    @Test
    void mergeProperty() {
        final List<Demo> demos = Arrays.asList(
            new Demo("p0", null, null),
            new Demo("p1", 1L, null),
            new Demo("p2", 1L, null),
            new Demo("p3", 2L, null));

        final Prop p1 = new Prop(1L, "n1");
        final Prop p2 = new Prop(2L, "n2");

        final List<Prop> props = Arrays.asList(p1, p2);

        final List<Demo> result = MergeUtils.merge(demos, props,
            (d, p) -> d.demoPropId != null && d.demoPropId.equals(p.id),
            (demo, prop) -> {
                prop.ifPresent(demo::setDemoProp);
            });

        assertThat(result).extracting(Demo::getProp1, Demo::getDemoProp)
            .contains(
                tuple("p0", null),
                tuple("p1", p1),
                tuple("p2", p1),
                tuple("p3", p2)
            );
    }

    @Test
    void toOneToManyMap() throws Exception {
        final List<Product> products = Arrays.asList(
            new Product(1L, "n1", "c1-1", "c2-2", 2L),
            new Product(1L, "n1", "c1-1", "c2-2", 2L),
            new Product(2L, "n1", "c1-1", "c2-2", 2L),
            new Product(2L, "n1", "c1-2", "c2-2", 2L),
            new Product(3L, "n1", "c1-1", "c2-2", 2L)
        );
        final List<Product2> results = MergeUtils.merge(products,
            (p, p2) -> p.id.equals(p2.id),
            (p, p2) -> {
                if (p2.getDynamicData() == null) {
                    p2.setDynamicData(new HashMap<>());
                }
                final String key = String.format("%s-%s", p.getCategory1(),
                    p.getCategory2());
                p2.getDynamicData().merge(key, p.count, Long::sum);
            },
            p -> new Product2(p.id, p.name)
        );
        System.out.println(Json.DEFAULT.write(results));
        // language=JSON
        JSONAssert.assertEquals("[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"n1\",\n" +
            "    \"dynamicData\": {\n" +
            "      \"c1-1-c2-2\": 4\n" +
            "    }\n" +
            "  }, {\n" +
            "  \"id\": 2,\n" +
            "  \"name\": \"n1\",\n" +
            "  \"dynamicData\": {\n" +
            "    \"c1-1-c2-2\": 2,\n" +
            "    \"c1-2-c2-2\": 2\n" +
            "  }\n" +
            "}, {\n" +
            "  \"id\": 3,\n" +
            "  \"name\": \"n1\",\n" +
            "  \"dynamicData\": {\n" +
            "    \"c1-1-c2-2\": 2\n" +
            "  }\n" +
            "}\n" +
            "]", Json.DEFAULT.write(results), false);
    }

    @Test
    void toOneToManyMap2() throws Exception {
        final List<Product> products = Arrays.asList(
            new Product(1L, "n1", "c1-1", "c2-2", 2L),
            new Product(1L, "n1", "c1-1", "c2-2", 2L),
            new Product(2L, "n1", "c1-1", "c2-2", 2L),
            new Product(2L, "n1", "c1-2", "c2-2", 2L),
            new Product(3L, "n1", "c1-1", "c2-2", 2L)
        );

        final List<Product> results = MergeUtils.merge(products,
            (p1, p2) -> p1.id.equals(p2.id),
            (p1, p2) -> {
                if (p2.getDynamicData() == null) {
                    p2.setDynamicData(new HashMap<>());
                }
                final String key = String.format("%s-%s", p1.getCategory1(),
                    p1.getCategory2());
                p2.getDynamicData().merge(key, p1.count, Long::sum);
            });
        // language=JSON
        JSONAssert.assertEquals("[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"n1\",\n" +
            "    \"category1\": \"c1-1\",\n" +
            "    \"category2\": \"c2-2\",\n" +
            "    \"count\": 2,\n" +
            "    \"dynamicData\": {\n" +
            "      \"c1-1-c2-2\": 4\n" +
            "    }\n" +
            "  }, {\n" +
            "  \"id\": 2,\n" +
            "  \"name\": \"n1\",\n" +
            "  \"category1\": \"c1-1\",\n" +
            "  \"category2\": \"c2-2\",\n" +
            "  \"count\": 2,\n" +
            "  \"dynamicData\": {\n" +
            "    \"c1-1-c2-2\": 2,\n" +
            "    \"c1-2-c2-2\": 2\n" +
            "  }\n" +
            "}, {\n" +
            "  \"id\": 3,\n" +
            "  \"name\": \"n1\",\n" +
            "  \"category1\": \"c1-1\",\n" +
            "  \"category2\": \"c2-2\",\n" +
            "  \"count\": 2,\n" +
            "  \"dynamicData\": {\n" +
            "    \"c1-1-c2-2\": 2\n" +
            "  }\n" +
            "}\n" +
            "]", Json.DEFAULT.write(results), false);
    }

    @Data
    @AllArgsConstructor
    static class Demo {
        String prop1;

        Long demoPropId;

        Prop demoProp;
    }

    @Data
    @AllArgsConstructor
    static class Prop {
        Long id;

        String name;
    }

    @Data
    @FieldNameConstants
    static class Product {
        final Long id;

        final String name;

        final String category1;

        final String category2;

        final Long count;

        Map<String, Long> dynamicData;
    }

    @Data
    @FieldNameConstants
    static class Product2 {
        final Long id;

        final String name;

        Map<String, Long> dynamicData;
    }
}