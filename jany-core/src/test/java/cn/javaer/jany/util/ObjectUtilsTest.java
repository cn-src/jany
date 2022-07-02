package cn.javaer.jany.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author cn-src
 */
class ObjectUtilsTest {

    @Test
    void testAllEquals() {
        Assertions.assertEquals(true, ObjectUtils.allEquals("n1", "n1", "n1"));
        Assertions.assertEquals(false, ObjectUtils.allEquals("n1", "n1", "n2"));
    }

    @Test
    void testAllNotEquals() {
        Assertions.assertEquals(true, ObjectUtils.notAllEquals("n1", "n2", "n3"));
        Assertions.assertEquals(false, ObjectUtils.notAllEquals("n1", "n1", "n1"));
    }

    @Test
    void testAnyEquals() {
        Assertions.assertEquals(true, ObjectUtils.anyEquals("n1", "n1", "n2"));
        Assertions.assertEquals(false, ObjectUtils.anyEquals("n1", "n2", "n3"));
    }

    @Test
    void testAnyNotEquals() {
        Assertions.assertEquals(true, ObjectUtils.notAnyEquals("n1", "n2", "n3"));
        Assertions.assertEquals(false, ObjectUtils.notAnyEquals("n1", "n1", "n2"));
    }
}