package cn.javaer.jany.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * @author cn-src
 */
public interface IoUtils {

    /**
     * 读取 properties 文件，并关闭流.
     *
     * @param input the input
     *
     * @return the properties
     */
    static Properties readProperties(final InputStream input) {
        final Properties props = new Properties();
        try (final InputStream in = input) {
            props.load(in);
        }
        catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
        return props;
    }
}