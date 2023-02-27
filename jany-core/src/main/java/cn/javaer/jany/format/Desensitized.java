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

package cn.javaer.jany.format;

import cn.hutool.core.util.DesensitizedUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author cn-src
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface Desensitized {

    Type type();

    enum Type {
        CHINESE_NAME(DesensitizedUtil::chineseName),
        ID_CARD(str -> DesensitizedUtil.idCardNum(str, 4, 4)),
        FIXED_PHONE(DesensitizedUtil::fixedPhone),
        MOBILE_PHONE(DesensitizedUtil::mobilePhone),
        EMAIL(DesensitizedUtil::email),
        CAR_LICENSE(DesensitizedUtil::carLicense),
        BANK_CARD(DesensitizedUtil::bankCard),
        ;

        private final UnaryOperator<String> fn;

        Type(UnaryOperator<String> fn) {
            this.fn = fn;
        }

        public Function<String, String> fn() {
            return fn;
        }
    }
}