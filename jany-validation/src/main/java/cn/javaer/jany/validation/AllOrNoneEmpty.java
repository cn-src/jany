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

import cn.javaer.jany.validation.AllOrNoneEmpty.List;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 一组数据要么同时为空，要么同时不为空。
 *
 * @author cn-src
 */
@Documented
@Constraint(validatedBy = AllOrNoneEmptyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface AllOrNoneEmpty {

    String message() default "{jany.validation.constraints.NotEmptyGroup.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] value();

    @Target({ElementType.TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        AllOrNoneEmpty[] value();
    }
}