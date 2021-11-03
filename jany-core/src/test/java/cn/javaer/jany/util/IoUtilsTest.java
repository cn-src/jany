package cn.javaer.jany.util;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class IoUtilsTest {

    @Test
    void readProperties() {
        final Properties props =
            IoUtils.readProperties(IoUtilsTest.class.getResourceAsStream("IoUtilsTest.properties"));
        assertThat(props.getProperty("demo")).isEqualTo("test");
    }
}