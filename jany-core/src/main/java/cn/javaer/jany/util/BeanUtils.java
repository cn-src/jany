package cn.javaer.jany.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author cn-src
 */
public class BeanUtils extends BeanUtil {

    public static TreeMap<String, Object> beanToTreeMap(Object bean, boolean ignoreEmptyValue) {
        if (null == bean) {
            return null;
        }

        final Map<String, Object> map = BeanUtil.beanToMap(bean);
        final TreeMap<String, Object> result = new TreeMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (ignoreEmptyValue && ObjectUtil.isEmpty(entry.getValue())) {
                continue;
            }
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}