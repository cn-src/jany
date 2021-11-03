package cn.javaer.jany.test;

import cn.hutool.core.map.MapUtil;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author cn-src
 */
class JsonAssertTest {

    @Test
    void assertEquals_ok() {
        JsonAssert.assertEquals("classpath:/test.json",
            MapUtil.builder().put("k1", "v1").put("k2", Arrays.array("v2_1", "v2_2")).build());
    }

    @Test
    void assertEquals_ok_ignore_order() {
        JsonAssert.assertEquals("classpath:/test2.json",
            MapUtil.builder().put("k1", "v1").put("k2", Arrays.array("v2_1", "v2_2")).build());
    }

    @Test
    void assertEquals_fail() {
        Assertions.assertThrows(AssertionError.class,
            () -> JsonAssert.assertEquals("classpath:/test.json",
                MapUtil.builder().put("k1", "vv").build()));
    }

    @Test
    void assertEqualsOrder_ok() {
        JsonAssert.assertEqualsAndOrder("classpath:/test.json",
            MapUtil.builder().put("k1", "v1").put("k2", Arrays.array("v2_1", "v2_2")).build());
    }

    @Test
    void assertEqualsOrder_fail() {
        Assertions.assertThrows(AssertionError.class,
            () -> JsonAssert.assertEqualsAndOrder("classpath:/test.json",
                MapUtil.builder().put("k1", "vv").build()));
    }

    @Test
    void assertEqualsOrder_fail_order() {
        Assertions.assertThrows(AssertionError.class, () -> {
            JsonAssert.assertEqualsAndOrder("classpath:/test2.json",
                MapUtil.builder().put("k1", "v1").put("k2", Arrays.array("v2_1", "v2_2")).build());
        });
    }
}