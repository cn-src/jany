package cn.javaer.jany.validation;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author cn-src
 */

public class AllOrNoneEmptyValidator implements ConstraintValidator<AllOrNoneEmpty, Object> {

    private String[] fields;

    @Override
    public void initialize(AllOrNoneEmpty constraintAnnotation) {
        this.fields = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (ArrayUtil.isEmpty(fields)) {
            return true;
        }
        int i = 0;
        for (String field : fields) {
            if (ObjectUtil.isEmpty(ReflectUtil.getFieldValue(value, field))) {
                i++;
            }
        }
        return i == 0 || fields.length == i;
    }
}