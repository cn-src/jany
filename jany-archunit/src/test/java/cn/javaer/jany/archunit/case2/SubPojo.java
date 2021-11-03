package cn.javaer.jany.archunit.case2;

import cn.javaer.jany.archunit.SubFields;
import lombok.Data;

/**
 * @author cn-src
 */
@Data
@SubFields(Pojo.class)
public class SubPojo {
    private String noField;
}