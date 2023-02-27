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

package cn.javaer.jany.spring.transaction;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Component} and {@link Transactional}.
 * <p>But default, a transaction will be rolling back on {@link Throwable}.
 *
 * @author cn-src
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Controller
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public @interface TxReadController {

    @AliasFor(attribute = "value", annotation = Component.class)
    String value() default "";

    @AliasFor(attribute = "value", annotation = Component.class)
    String name() default "";

    @AliasFor(attribute = "transactionManager", annotation = Transactional.class)
    String transactionManager() default "";

    @AliasFor(annotation = Transactional.class)
    Propagation propagation() default Propagation.REQUIRED;

    @AliasFor(annotation = Transactional.class)
    Isolation isolation() default Isolation.DEFAULT;

    @AliasFor(annotation = Transactional.class)
    int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

    @AliasFor(annotation = Transactional.class)
    Class<? extends Throwable>[] noRollbackFor() default {};

    @AliasFor(annotation = Transactional.class)
    String[] noRollbackForClassName() default {};
}