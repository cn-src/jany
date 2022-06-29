package cn.javaer.jany.util;

import cn.hutool.core.collection.ListUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author cn-src
 */
class ListUtilsTest {

    @Test
    void toList() {
        Object obj = null;
        Assertions.assertThat(ListUtils.toList(obj)).isNull();

        obj = ListUtil.of("demo");
        Assertions.assertThat(ListUtils.toList(obj)).hasSize(1);

        obj = CollUtils.toCollection(ListUtil.of("demo"));
        Assertions.assertThat(ListUtils.toList(obj)).hasSize(1);

        obj = "";
        Assertions.assertThat(ListUtils.toList(obj)).hasSize(1);
    }
}