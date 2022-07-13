package cn.javaer.jany.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
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

    /**
     * 可选执行函数。如果 value 值不为空，则执行 fun 返回的消费者函数。
     * <p>
     * <pre>
     *  样例：xxx.opt(q -> q.name::eq, "name1")
     *  </pre>
     *
     * @param fun 一个函数，它接受根对象并返回一个接受该值的消费者。
     * @param value 要传递给消费者的值。
     *
     * @return this
     */
    public <V> OptChain<T> opt(@NotNull final Function<T, Consumer<V>> fun, final V value) {
        Assert.notNull(fun, "fun must not be null");
        if (ObjectUtil.isEmpty(value)) {
            return this;
        }
        fun.apply(root).accept(value);
        return this;
    }

    /**
     * 可选执行函数。如果 value1 和 value2 只都不为空，则执行 fun 返回的消费者函数。
     * <p>
     * <pre>
     *  样例：xxx.opt(q -> q.name::eq, "name1", "name1")
     *  </pre>
     *
     * @param fun 一个函数，它接受根对象并返回一个接受该值的消费者。
     * @param value1 要传递给消费者的值。
     * @param value2 要传递给消费者的值。
     *
     * @return this
     */
    public <V1, V2> OptChain<T>
    opt(@NotNull final Function<T, BiConsumer<V1, V2>> fun, final V1 value1, V2 value2) {
        Assert.notNull(fun, "fun must not be null");
        if (ObjectUtil.isEmpty(value1) || ObjectUtil.isEmpty(value2)) {
            return this;
        }
        fun.apply(root).accept(value1, value2);
        return this;
    }

    /**
     * 直接执行函数。
     *
     * @param fun 要执行的函数。
     *
     * @return this
     */
    public OptChain<T> fn(@NotNull Consumer<T> fun) {
        Assert.notNull(fun, "fun must not be null");
        fun.accept(root);
        return this;
    }

    /**
     * 可选执行函数。如果 condition 为 true，则执行 fun 消费者函数。
     * <p>
     * <pre>
     *  样例：xxx.opt(q -> q.name::eq, "name1", "name1")
     *  </pre>
     *
     * @param fun 消费者函数，它接受根对象并执行。
     * @param condition 是否要执行消费者函数的条件函数。
     *
     * @return this
     */
    public OptChain<T> opt(@NotNull Consumer<T> fun, final BooleanSupplier condition) {
        Assert.notNull(fun, "fun must not be null");

        if (!condition.getAsBoolean()) {
            return this;
        }
        fun.accept(root);
        return this;
    }

    public T root() {
        return root;
    }

    public static <T> OptChain<T> of(@NotNull T root) {
        Assert.notNull(root, "root object must not be null");
        return new OptChain<>(root);
    }
}