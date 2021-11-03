package cn.javaer.jany.util;

import java.util.function.Function;

/**
 * @author cn-src
 */
public interface Empty {
    @SuppressWarnings("rawtypes") Function FUNCTION = o -> null;

    /**
     * empty function.
     *
     * @param <T> the type parameter
     * @param <R> the type parameter
     *
     * @return the function
     */
    @SuppressWarnings("unchecked")
    static <T, R> Function<T, R> function() {
        return FUNCTION;
    }
}