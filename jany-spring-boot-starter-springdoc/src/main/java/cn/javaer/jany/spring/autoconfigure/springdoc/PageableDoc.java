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
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;

/**
 * @author cn-src
 */
@Data
@ParameterObject
@Schema(name = "Pageable")
public class PageableDoc {

    @Schema(name = "page",
        description = "分页-页码",
        minimum = "1",
        defaultValue = "1")
    Integer page;

    @Schema(name = "size",
        description = "分页-大小",
        minimum = "1",
        defaultValue = "20")
    Integer size;

    @Schema(name = "sort",
        description = "分页-排序, 指定字段降序: 'sort=field1,field2,desc&sort=field1,field2',排序方式默认为升序(asc)"
    )
    List<String> sort;
}