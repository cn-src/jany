/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.jackson;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.beans.ConstructorProperties;

import static org.assertj.core.api.Assertions.assertThat;

class JsonTest {

    @Test
    void objectMapper() {
    }

    @Test
    void write_DEFAULT() {
        final String json1 = Json.DEFAULT.write(new Demo("name", "adrName"));
        assertThat(json1).isEqualTo("{\"name\":\"name\",\"adrName\":\"adrName\"}");

        final String json2 = Json.DEFAULT.write(new Demo("name", ""));
        assertThat(json2).isEqualTo("{\"name\":\"name\",\"adrName\":\"\"}");
    }

    @Test
    void write_NON_EMPTY() {
        final String json1 = Json.NON_EMPTY.write(new Demo("name", "adrName"));
        assertThat(json1).isEqualTo("{\"name\":\"name\",\"adrName\":\"adrName\"}");

        final String json2 = Json.NON_EMPTY.write(new Demo("name", ""));
        assertThat(json2).isEqualTo("{\"name\":\"name\"}");
    }

    @Test
    void read() {
        final Demo demo1 = Json.LENIENT.read("{\"name\":\"name\",\"adrName\":\"adrName\"}", Demo.class);
        assertThat(demo1).extracting(Demo::getName, Demo::getAdrName)
                .containsExactly("name", "adrName");
        //language=JSON
        final Demo demoUnknown = Json.LENIENT.read("{\"name\":\"name\",\"adrName\":\"adrName\", \"abc\": \"abc\"}", Demo.class);
        assertThat(demoUnknown).extracting(Demo::getName, Demo::getAdrName)
                .containsExactly("name", "adrName");
    }


    @Data
    static class Demo {

        @ConstructorProperties({"name", "adrName"})
        public Demo(String name, String adrName) {
            this.name = name;
            this.adrName = adrName;
        }

        private String name;
        private String adrName;
    }
}