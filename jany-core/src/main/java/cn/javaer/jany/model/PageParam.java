package cn.javaer.jany.model;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

/**
 * 分页参数.
 *
 * @author cn-src
 */
@Data
@ParameterObject
@Setter(AccessLevel.PACKAGE)
public class PageParam {

    PageParam() {
        this.page = 1;
        this.size = 20;
    }

    PageParam(final int page, final int size, final int offset) {
        this.page = Math.max(page, 1);
        this.size = Math.max(size, 1);
        this.offset = Math.max(offset, 0);
    }

    @Parameter(description = "分页-页码", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "1"))
    int page;

    @Parameter(description = "分页-大小", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "20"))
    int size;

    @Hidden
    int offset;

    public static PageParam of(final int page, final int size) {
        return new PageParam(page, size, (page - 1) * size);
    }
}