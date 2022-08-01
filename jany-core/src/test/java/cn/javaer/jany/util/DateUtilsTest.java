package cn.javaer.jany.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cn-src
 */
class DateUtilsTest {

    @Test
    void humanReadable() {
        assertEquals("1ms", DateUtils.humanReadable(1000000L));

        assertEquals("1.0s", DateUtils.humanReadable(1000000L * 1000));
        assertEquals("1.3s", DateUtils.humanReadable(1000000L * 1300));
        assertEquals("1.5s", DateUtils.humanReadable(1000000L * 1500));
        assertEquals("1.8s", DateUtils.humanReadable(1000000L * 1800));

        assertEquals("1.0m", DateUtils.humanReadable(1000000L * 1000 * 60));
        assertEquals("1.5m", DateUtils.humanReadable(1000000L * 1000 * 90));
        assertEquals("2.0m", DateUtils.humanReadable(1000000L * 1000 * 60 * 2));

        assertEquals("1.0h", DateUtils.humanReadable(1000000L * 1000 * 60 * 60));
        assertEquals("1.5h", DateUtils.humanReadable(1000000L * 1000 * 60 * 90));
        assertEquals("2.0h", DateUtils.humanReadable(1000000L * 1000 * 60 * 60 * 2));

        assertEquals("1.0d", DateUtils.humanReadable(1000000L * 1000 * 60 * 60 * 24));
        assertEquals("1.5d", DateUtils.humanReadable(1000000L * 1000 * 60 * 60 * 36));
        assertEquals("2.0d", DateUtils.humanReadable(1000000L * 1000 * 60 * 60 * 24 * 2));
    }
}