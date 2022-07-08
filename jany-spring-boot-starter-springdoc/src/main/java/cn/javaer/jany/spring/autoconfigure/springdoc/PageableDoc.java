package cn.javaer.jany.spring.autoconfigure.springdoc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

import java.util.List;

/**
 * @author cn-src
 */
@Data
@ParameterObject
@Schema(name = "Pageable")
public class PageableDoc {

    @Schema(name = "page",
        description = "分页-页码",
        minimum = "1",
        defaultValue = "1")
    Integer page;

    @Schema(name = "size",
        description = "分页-大小",
        minimum = "1",
        defaultValue = "20")
    Integer size;

    @Schema(name = "sort",
        description = "分页-排序, 指定字段降序: 'sort=field1,field2,desc&sort=field1,field2',排序方式默认为升序(asc)"
    )
    List<String> sort;
}