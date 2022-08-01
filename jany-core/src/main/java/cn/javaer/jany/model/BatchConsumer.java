package cn.javaer.jany.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/**
 * 批处理消费者函数，适用于简单的批处理场景.
 * <pre>
 * BatchConsumer<String> batchConsumer = new BatchConsumer<>(value -> 0);
 * final List<String> demo = new ArrayList<>();
 * demo.stream()
 *     .onClose(batchConsumer)
 *     .forEach(batchConsumer);
 * </pre>
 *
 * @author cn-src
 */
public class BatchConsumer<T> implements Consumer<T>, Runnable {

    private final int batchSize;

    private final ToIntFunction<List<T>> fn;

    private final List<T> data;

    public BatchConsumer(@NotNull final ToIntFunction<List<T>> fn) {
        this(fn, 100);
    }

    public BatchConsumer(@NotNull final ToIntFunction<List<T>> fn, final int batchSize) {
        this.fn = Objects.requireNonNull(fn);
        this.batchSize = batchSize;
        this.data = new ArrayList<>(batchSize);
    }

    @Override
    public void accept(final T t) {
        if (this.data.size() < this.batchSize) {
            this.data.add(t);
        }
        else {
            this.fn.applyAsInt(this.data);
            this.data.clear();
        }
    }

    @Override
    public void run() {
        if (this.data.size() > 0) {
            this.fn.applyAsInt(this.data);
            this.data.clear();
        }
    }
}