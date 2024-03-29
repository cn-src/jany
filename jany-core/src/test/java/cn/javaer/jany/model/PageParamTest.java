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

package cn.javaer.jany.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class PageParamTest {

    @Test
    @DisplayName("测试页码为 1, Offset 计算")
    void onePage() {
        final PageParam pageParam = PageParam.of(1, 20);
        assertThat(pageParam.getOffset()).isEqualTo(0L);
    }

    @Test
    @DisplayName("测试非第一页 Offset 计算")
    void nonOnePage() {
        final PageParam pageParam = PageParam.of(2, 20);
        assertThat(pageParam.getOffset()).isEqualTo(20L);
    }

    @Test
    @DisplayName("测试页码为 0, Offset 计算")
    void name1() {
        final PageParam pageParam = PageParam.of(0, 20);
        assertThat(pageParam.getOffset()).isEqualTo(0L);
    }

    @Test
    @DisplayName("测试页码为负, Offset 计算")
    void name2() {
        final PageParam pageParam = PageParam.of(-1, 20);
        assertThat(pageParam.getOffset()).isEqualTo(0L);
    }

    @Test
    void ofPageable() {
        // Pageable 页码从 0 开始。
        final Pageable pageRequest = PageRequest.of(2, 5, org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "prop1"));
        final PageParam pageParam = PageParam.of(pageRequest);
        assertThat(pageParam)
            .extracting(PageParam::getPage, PageParam::getSize)
            .contains(3, 5);
        assertThat(pageParam.getSort().isSorted()).isTrue();
        assertThat(pageParam.getSort().getOrders().size()).isEqualTo(1);
        assertThat(pageParam.getSort().getOrders().get(0).getProperty()).isEqualTo("prop1");
        assertThat(pageParam.getSort().getOrders().get(0).getDirection()).isEqualTo(Sort.Direction.DESC);
    }
}