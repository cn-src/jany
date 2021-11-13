package cn.javaer.jany.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import org.springdoc.api.annotations.ParameterObject;

/**
 * 分页参数.
 *
 * @author cn-src
 */
@Value
@ParameterObject
public class PageParam {
    
    public PageParam(final int page, final int size) {
        this.page = Math.max(page, 1);
        this.size = Math.max(size, 1);
    }

    @Parameter(description = "分页-页码", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "1"))
    int page;

    @Parameter(description = "分页-大小", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "20"))
    int size;

    public static PageParam of(final int page, final int size) {
        return new PageParam(page, size);
    }

    public int getOffset() {
        return Math.max((page - 1) * size, 0);
    }
}