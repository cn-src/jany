package cn.javaer.jany.util;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * @author cn-src
 */
public class MapMatcher<K, R> {
    private final Map<K, Function<K, R>> mapping;

    public MapMatcher(Map<K, Function<K, R>> mapping) {
        this.mapping = mapping;
    }

    @Nullable
    public R applyBy(K key) {
        Function<K, R> fn = mapping.get(key);
        if (fn != null) {
            return fn.apply(key);
        }
        return null;
    }

    public R applyOrThrowBy(K key) {
        Function<K, R> fn = mapping.get(key);
        if (fn != null) {
            fn.apply(key);
        }
        else {
            throw new NoSuchElementException("Not match, key: " + key);
        }
        return null;
    }

    public static <K, R> MapMatcher<K, R> of(K k, Function<K, R> fn) {
        return new MapMatcher<>(Map.of(k, fn));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
            k6, fn6));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6,
                                             K k7, Function<K, R> fn7) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
            k6, fn6, k7, fn7));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6,
                                             K k7, Function<K, R> fn7,
                                             K k8, Function<K, R> fn8) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
            k6, fn6, k7, fn7, k8, fn8));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6,
                                             K k7, Function<K, R> fn7,
                                             K k8, Function<K, R> fn8,
                                             K k9, Function<K, R> fn9) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
            k6, fn6, k7, fn7, k8, fn8, k9, fn9));
    }

    public static <K, R> MapMatcher<K, R> of(K k1, Function<K, R> fn1,
                                             K k2, Function<K, R> fn2,
                                             K k3, Function<K, R> fn3,
                                             K k4, Function<K, R> fn4,
                                             K k5, Function<K, R> fn5,
                                             K k6, Function<K, R> fn6,
                                             K k7, Function<K, R> fn7,
                                             K k8, Function<K, R> fn8,
                                             K k9, Function<K, R> fn9,
                                             K k10, Function<K, R> fn10) {
        return new MapMatcher<>(Map.of(k1, fn1, k2, fn2, k3, fn3, k4, fn4, k5, fn5,
            k6, fn6, k7, fn7, k8, fn8, k9, fn9, k10, fn10));
    }
}