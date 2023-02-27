/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.model;

import cn.hutool.core.util.IdUtil;
import cn.javaer.jany.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/**
 * 批处理消费者函数，适用于简单的批处理场景.
 * <p><pre>{@code
 * BatchConsumer<String> batchConsumer = new BatchConsumer<>(value -> 0);
 * final List<String> demo = new ArrayList<>();
 * demo.stream()
 *     .onClose(batchConsumer)
 *     .forEach(batchConsumer);
 * }</pre>
 *
 * @author cn-src
 */
@Slf4j
public class BatchConsumer<T> implements Consumer<T>, Runnable {

    private final int batchSize;

    private final ToIntFunction<List<T>> fn;

    private final List<T> data;

    private final String batchId;

    private final long startTimeNanos;

    private long lastTimeNanos;

    private long totalRows;

    private int totalPages;

    public BatchConsumer(@NotNull final ToIntFunction<List<T>> fn) {
        this(IdUtil.simpleUUID(), fn, 100);
    }

    public BatchConsumer(@NotNull String batchId, @NotNull final ToIntFunction<List<T>> fn) {
        this(batchId, fn, 100);
    }

    public BatchConsumer(@NotNull String batchId,
                         @NotNull final ToIntFunction<List<T>> fn, final int batchSize) {
        this.fn = Objects.requireNonNull(fn);
        this.batchSize = batchSize;
        this.data = new ArrayList<>(batchSize);
        this.batchId = batchId;
        this.startTimeNanos = System.nanoTime();
        this.lastTimeNanos = startTimeNanos;
    }

    @Override
    public void accept(final T t) {
        if (this.data.size() < this.batchSize) {
            this.data.add(t);
        }
        else {
            process();
        }
    }

    @Override
    public void run() {
        if (!this.data.isEmpty()) {
            process();
        }
        String time = DateUtils.humanReadable(System.nanoTime() - this.startTimeNanos);
        log.info("Batch: '{}', batchSize: {}, totalPages: {}, totalRows: {}, totalTime: {}, end.",
            this.batchId, batchSize, totalPages, totalRows, time);
    }

    private void process() {
        final int size = this.data.size();
        this.fn.applyAsInt(this.data);
        this.data.clear();
        totalPages++;
        totalRows += size;
        // logging
        String time = DateUtils.humanReadable(System.nanoTime() - this.lastTimeNanos);
        this.lastTimeNanos = System.nanoTime();
        log.info("Batch: '{}', size: {}, time: {}, processing.",
            this.batchId, size, time);
    }
}