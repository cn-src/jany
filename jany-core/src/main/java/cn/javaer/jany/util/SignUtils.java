package cn.javaer.jany.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;

import java.util.Map;

/**
 * @author cn-src
 */
public class SignUtils {

    /**
     * 为 bean 生成签名，按属性的自然顺序排序，以 key=value 和 & 连接，最后加上 key=keyValue，进行 md5 签名。
     *
     * @param bean 签署的对象
     * @param key 用于签署请求的密钥。
     *
     * @return 签名字符串。
     */
    public static String sign(Object bean, String key) {
        Assert.notNull(bean, "bean must not be null");
        Assert.notEmpty(key, "key must not be empty");

        final Map<String, Object> params = BeanUtils.beanToTreeMap(bean, true);
        StringBuilder sb = new StringBuilder();
        params.forEach((k, v) -> sb.append(k).append("=").append(v).append("&"));
        sb.append("key=").append(key);
        return SecureUtil.md5(sb.toString());
    }
}