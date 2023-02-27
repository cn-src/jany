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

package cn.javaer.jany.archunit;

import cn.javaer.jany.archunit.case1.Pojo;
import cn.javaer.jany.archunit.case1.SubPojo;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author cn-src
 */
class SubFieldsConditionTest {

    @Test
    @DisplayName("测试正常子集")
    void testCheck() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .importClasses(
                Pojo.class,
                SubPojo.class);
        classes().should(new SubFieldsCondition("demo test"))
            .check(importedClasses);
    }

    @Test
    @DisplayName("测试不是子集，字段名称不一致")
    void testCheck2() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .importClasses(
                cn.javaer.jany.archunit.case2.Pojo.class,
                cn.javaer.jany.archunit.case2.SubPojo.class);
        assertThatExceptionOfType(AssertionError.class)
            .isThrownBy(() ->
                classes().should(new SubFieldsCondition("demo test")).check(importedClasses)
            );
    }

    @Test
    @DisplayName("测试不是子集，字段名一样，但是字段类型不一样")
    void testCheck3() {
        final JavaClasses importedClasses = new ClassFileImporter()
            .importClasses(
                cn.javaer.jany.archunit.case3.Pojo.class,
                cn.javaer.jany.archunit.case3.SubPojo.class);
        assertThatExceptionOfType(AssertionError.class)
            .isThrownBy(() ->
                classes().should(new SubFieldsCondition("demo test")).check(importedClasses)
            );
    }
}