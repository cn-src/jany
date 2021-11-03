package cn.javaer.jany.archunit.case1;

import cn.javaer.jany.archunit.SubFields;
import lombok.Data;

/**
 * @author cn-src
 */
@Data
@SubFields(Pojo.class)
public class SubPojo {
    private String field1;
}