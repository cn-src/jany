package cn.javaer.jany.util;

import cn.hutool.core.io.IoUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * @author cn-src
 */
public class IoUtils extends IoUtil {

    /**
     * 从输入流中读取属性文件并返回属性对象。
     *
     * @param input 要从中读取属性的输入流。
     *
     * @return 一个属性对象
     */
    public static Properties readProperties(final InputStream input) {
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