package cn.javaer.jany.model;

import cn.javaer.jany.model.PageParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}