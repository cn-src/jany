package cn.javaer.jany.validation;

import javax.validation.ConstraintValidatorContext;
import java.util.Set;

/**
 * @author cn-src
 */
public class ExcelValidator extends AbstractFileTypeValidator<Excel> {

    @Override
    public void initialize(Excel constraintAnnotation) {
        this.suffixes = Set.of("xls", "xlsx", "xlsm");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return super.isValid(value, context);
    }
}