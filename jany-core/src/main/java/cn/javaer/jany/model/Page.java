package cn.javaer.jany.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * 分页结果.
 *
 * @author cn-src
 */
@Data
@Setter(AccessLevel.PACKAGE)
public class Page<T> {

    @SuppressWarnings("rawtypes")
    public final static Page EMPTY = new Page<>(Collections.emptyList(), 0);

    Page(final List<T> content, final long total) {
        if (total < 0) {
            throw new IllegalArgumentException("'total' must not less than 0");
        }
        this.content = content;
        this.total = total;
    }

    @Schema(description = "分页-内容")
    private List<T> content;

    @Schema(description = "分页-总数")
    private long total;

    @Schema(description = "是否为空")
    public boolean isEmpty() {
        return this.content == null || this.content.isEmpty();
    }

    public static <T> Page<T> of(final List<T> content, final long total) {
        return new Page<>(content, total);
    }

    @SuppressWarnings("unchecked")
    public static <T> Page<T> empty() {
        return (Page<T>) EMPTY;
    }
}