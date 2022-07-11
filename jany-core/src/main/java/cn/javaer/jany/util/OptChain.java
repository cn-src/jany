package cn.javaer.jany.util;

import cn.hutool.core.util.ObjectUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 可选链，用于构建可选操作.
 *
 * @author cn-src
 */
public class OptChain<T> {
    private final T root;

    private OptChain(T root) {
        this.root = root;
    }

    public <V> OptChain<T> opt(@NotNull final Function<T, Consumer<V>> fun, final V value) {
        if (ObjectUtil.isEmpty(value)) {
            return this;
        }
        fun.apply(root).accept(value);
        return this;
    }

    public <V1, V2> OptChain<T>
    opt(@NotNull final Function<T, BiConsumer<V1, V2>> fun, final V1 value1, V2 value2) {
        if (ObjectUtil.isEmpty(value1) || ObjectUtil.isEmpty(value2)) {
            return this;
        }
        fun.apply(root).accept(value1, value2);
        return this;
    }

    public OptChain<T> call(Consumer<T> fun) {
        fun.accept(root);
        return this;
    }

    public T root() {
        return root;
    }

    public static <T> OptChain<T> of(T root) {
        return new OptChain<>(root);
    }
}