package cn.javaer.jany.model;

import lombok.Value;

/**
 * @author cn-src
 */
@Deprecated
@Value(staticConstructor = "of")
public class KeyValue<V> {
    String key;
    V value;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final KeyValue EMPTY = new KeyValue(null, null);
}