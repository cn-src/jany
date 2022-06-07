package cn.javaer.jany.validation;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author cn-src
 */
public class AbstractFileTypeValidator<A extends Annotation>
    implements ConstraintValidator<A, Object> {

    private static final String MULTIPART_FILE = "org.springframework.web.multipart.MultipartFile";

    protected Set<String> suffixes;

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
            return isSuffix(str.toString());
        }
        if (value instanceof File) {
            File file = (File) value;
            if (file.isDirectory()) {
                return false;
            }
            return isSuffix(file.getName());
        }
        if (ClassLoaderUtil.isPresent(MULTIPART_FILE)) {
            return isSuffix(value);
        }
        return false;
    }

    boolean isSuffix(final String fileName) {
        final String suffix = FileUtil.getSuffix(fileName);
        if (null == suffix) {
            return false;
        }
        return suffixes.contains(suffix);
    }

    boolean isSuffix(final Object file) {
        if (!(file instanceof MultipartFile)) {
            return false;
        }
        final MultipartFile multipartFile = (MultipartFile) file;
        if (multipartFile.isEmpty()) {
            return false;
        }
        return isSuffix(multipartFile.getOriginalFilename());
    }
}