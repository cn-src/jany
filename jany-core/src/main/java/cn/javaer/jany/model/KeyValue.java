package cn.javaer.jany.model;

import lombok.Value;

/**
 * @author cn-src
 */
@Value(staticConstructor = "of")
public class KeyValue<K, V> {
    K key;
    V value;
}