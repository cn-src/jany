package cn.javaer.jany.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import lombok.With;
import lombok.experimental.FieldNameConstants;
import org.springdoc.api.annotations.ParameterObject;

/**
 * 分页参数.
 *
 * @author cn-src
 */
@Value
@ParameterObject
@FieldNameConstants
public class PageParam {

    public static final int DEFAULT_PAGE = 1;

    public static final int DEFAULT_SIZE = 20;

    private PageParam(final int page, final int size, Sort sort) {
        this.page = Math.max(page, 1);
        this.size = Math.min(maxSize, Math.max(size, 1));
        this.sort = sort;
    }

    @Parameter(description = "分页-页码", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "1"))
    int page;

    @Parameter(description = "分页-大小", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "20"))
    int size;

    @Schema(name = "sort",
        description = "分页-排序, 指定字段降序: 'sort=field1,field2,desc&sort=field1,field2',排序方式默认为升序(asc)"
    )
    @With
    Sort sort;

    private static int maxSize = 2000;

    public static PageParam of(final int page, final int size) {
        return new PageParam(page, size, Sort.DEFAULT);
    }

    public static PageParam of(final int page, final int size, Sort sort) {
        return new PageParam(page, size, sort);
    }

    public int getOffset() {
        return Math.max((page - 1) * size, 0);
    }

    public static void setMaxSize(int maxSize) {
        PageParam.maxSize = maxSize;
    }
}