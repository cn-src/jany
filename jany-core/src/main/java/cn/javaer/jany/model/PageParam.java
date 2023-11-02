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

package cn.javaer.jany.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import lombok.With;
import lombok.experimental.FieldNameConstants;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 分页参数.
 *
 * @author cn-src
 */
@Value
@ParameterObject
@FieldNameConstants
public class PageParam {

    public static final int DEFAULT_PAGE = 1;

    public static final int DEFAULT_SIZE = 20;

    @Parameter(description = "分页-页码", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "1"))
    int page;

    @Parameter(description = "分页-大小", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "20"))
    int size;

    @With
    @Schema(name = "sort",
            description = "分页-排序, 指定字段降序: 'sort=field1,field2,desc&sort=field1,field2',排序方式默认为升序(asc)")
    Sort sort;

    private PageParam(final int page, final int size, Sort sort) {
        this.page = Math.max(page, 1);
        this.size = Math.max(size, 1);
        this.sort = sort;
    }

    public static PageParam of(final int page, final int size) {
        return new PageParam(page, size, Sort.DEFAULT);
    }

    public static PageParam of(final int page, final int size, Sort sort) {
        return new PageParam(page, size, sort);
    }

    /**
     * spring data Pageable 转 jany PageParam。
     *
     * @param pageable Pageable
     * @return PageParam
     */
    public static PageParam of(final Pageable pageable) {
        final org.springframework.data.domain.Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            Set<Sort.Order> orders = new LinkedHashSet<>();
            for (org.springframework.data.domain.Sort.Order order : sort) {
                final cn.javaer.jany.model.Sort.Direction direction =
                        cn.javaer.jany.model.Sort.Direction.valueOf(order.getDirection().name());
                orders.add(new cn.javaer.jany.model.Sort.Order(direction, order.getProperty()));
            }

            return PageParam.of(pageable.getPageNumber() + 1, pageable.getPageSize(),
                    cn.javaer.jany.model.Sort.by(new ArrayList<>(orders)));
        }
        return PageParam.of(pageable.getPageNumber() + 1, pageable.getPageSize());
    }

    public int getOffset() {
        return Math.max((page - 1) * size, 0);
    }
}