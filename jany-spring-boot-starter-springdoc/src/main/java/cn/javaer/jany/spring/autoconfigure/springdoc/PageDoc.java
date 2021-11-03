package cn.javaer.jany.spring.autoconfigure.springdoc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author cn-src
 */
@Data
@Schema(name = "Page")
public class PageDoc<T> {

    @Schema(description = "分页-内容")
    List<T> content;

    @Schema(description = "分页-总页数")
    int totalPages;

    @Schema(description = "分页-总条数")
    long totalElements;
}