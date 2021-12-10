package cn.javaer.jany.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springdoc.api.annotations.ParameterObject;

import java.beans.ConstructorProperties;

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

    @JsonCreator
    @ConstructorProperties({Fields.page, Fields.size, Fields.sort})
    public PageParam(final int page, final int size, Sort sort) {
        this.page = Math.max(page, 1);
        this.size = Math.max(size, 1);
        this.sort = sort;
    }

    @Parameter(description = "分页-页码", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "1"))
    int page;

    @Parameter(description = "分页-大小", schema =
    @Schema(type = "integer", minimum = "1", defaultValue = "20"))
    int size;

    Sort sort;

    public static PageParam of(final int page, final int size) {
        return new PageParam(page, size, Sort.DEFAULT);
    }

    public int getOffset() {
        return Math.max((page - 1) * size, 0);
    }
}