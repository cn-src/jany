package cn.javaer.jany.util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author cn-src
 */
public interface Empty {
    @SuppressWarnings("rawtypes") Function FUNCTION = o -> null;
    @SuppressWarnings("rawtypes") Supplier SUPPLIER = () -> null;

    /**
     * empty Function.
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

    /**
     * empty Supplier.
     *
     * @param <T> the type parameter
     *
     * @return the supplier
     */
    @SuppressWarnings("unchecked")
    static <T> Supplier<T> supplier() {
        return SUPPLIER;
    }
}