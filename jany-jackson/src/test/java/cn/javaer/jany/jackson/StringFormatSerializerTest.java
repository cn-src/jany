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

package cn.javaer.jany.jackson;

import cn.javaer.jany.format.Desensitized;
import cn.javaer.jany.format.StringFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StringFormatSerializerTest {
    static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        // objectMapper.registerModules(new JavaTimeModule(), new Jdk8Module(), new JanyModule());
        objectMapper.setAnnotationIntrospector(JanyJacksonAnnotationIntrospector.INSTANCE);
    }

    @Test
    void serialize() throws Exception {
        Demo demo = new Demo("230101199606195566", "str1", "  ", "  str3  ", " str4 ", "  ");
        String json = objectMapper.writeValueAsString(demo);
        System.out.println(json);
    }

    @Data
    @AllArgsConstructor
    @StringFormat
    static class Demo {
        @Desensitized(type = Desensitized.Type.ID_CARD)
        String desensitized;

        String str1;

        String str2;

        String str3;

        @StringFormat(apply = false)
        String str4;

        @StringFormat(emptyToNull = false)
        String str5;
    }
}