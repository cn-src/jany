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

import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.crypto.SecureUtil;

import java.util.Map;

/**
 * @author cn-src
 */
public class SignUtils {

    /**
     * 为 bean 生成签名，按属性的自然顺序排序，以 {@literal key=value 和 & } 连接，最后加上 key=keyValue，进行 md5 签名。
     *
     * @param bean 签署的对象
     * @param key 用于签署请求的密钥。
     *
     * @return 签名字符串。
     */
    public static String sign(Object bean, String key) {
        Assert.notNull(bean, "bean must not be null");
        Assert.notEmpty(key, "key must not be empty");

        final Map<String, Object> params = BeanUtils.beanToSortedMap(bean, true);
        StringBuilder sb = new StringBuilder();
        params.forEach((k, v) -> sb.append(k).append("=").append(v).append("&"));
        sb.append("key=").append(key);
        return SecureUtil.md5(sb.toString());
    }
}