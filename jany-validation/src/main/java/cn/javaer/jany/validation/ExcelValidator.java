package cn.javaer.jany.validation;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;

/**
 * @author cn-src
 */
public class ExcelValidator implements ConstraintValidator<Excel, Object> {

    private static final String MULTIPART_FILE = "org.springframework.web.multipart.MultipartFile";

    @Override
    public void initialize(Excel constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (null == value) {
            return true;
        }

        if (value instanceof CharSequence) {
            final CharSequence str = (CharSequence) value;
            if (str.length() == 0) {
                return true;
            }
            return isExcel(str.toString());
        }
        if (value instanceof File) {
            File file = (File) value;
            if (file.isDirectory()) {
                return false;
            }
            return isExcel(file.getName());
        }
        if (ClassLoaderUtil.isPresent(MULTIPART_FILE)) {
            return isExcel((MultipartFile) value);
        }
        return false;
    }

    boolean isExcel(final String fileName) {
        final String suffix = FileUtil.getSuffix(fileName);
        if (null == suffix) {
            return false;
        }
        return suffix.equalsIgnoreCase("xls") ||
            suffix.equalsIgnoreCase("xlsx") ||
            suffix.equalsIgnoreCase("xlsm");
    }

    boolean isExcel(final MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        return isExcel(file.getOriginalFilename());
    }
}