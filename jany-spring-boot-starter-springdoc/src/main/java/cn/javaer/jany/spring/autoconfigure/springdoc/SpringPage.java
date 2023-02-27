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

package cn.javaer.jany.spring.autoconfigure.springdoc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 命名成 SpringPage，避免默认情况下与 jany Page 对象的命名冲突（默认情况下 Swagger 只取 Class 的短称）。
 * <p>
 * 类上不能使用 @Schema 注解的 name 属性，否则范型失效。
 *
 * @author cn-src
 */
@Data
public class SpringPage<T> {

    @Schema(description = "分页-内容")
    List<T> content;

    @Schema(description = "分页-总页数")
    int totalPages;

    @Schema(description = "分页-总条数")
    long totalElements;
}