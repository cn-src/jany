package cn.javaer.jany.spring.autoconfigure.springdoc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 命名成 SpringPage，避免默认情况下的命名冲突。
 *
 * @author cn-src
 */
@Data
public class SpringPage<T> {

    @Schema(description = "分页-内容")
    List<T> content;

    @Schema(description = "分页-总页数")
    int totalPages;

    @Schema(description = "分页-总条数")
    long totalElements;
}