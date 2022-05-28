package cn.javaer.jany.validation;

import javax.validation.ConstraintValidatorContext;
import java.util.Set;

/**
 * @author cn-src
 */
public class FileTypeValidator extends AbstractFileTypeValidator<FileType> {

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.suffixes = Set.of(constraintAnnotation.suffixes());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return super.isValid(value, context);
    }
}