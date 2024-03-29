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

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dromara.hutool.core.array.ArrayUtil;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.util.ObjUtil;


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
            if (ObjUtil.isEmpty(FieldUtil.getFieldValue(value, field))) {
                i++;
            }
        }
        return i == 0 || fields.length == i;
    }
}