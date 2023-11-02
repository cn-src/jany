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

package cn.javaer.jany.util;

import org.dromara.hutool.core.io.IoUtil;

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