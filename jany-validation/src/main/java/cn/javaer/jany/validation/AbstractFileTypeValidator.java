/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.javaer.jany.validation;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

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