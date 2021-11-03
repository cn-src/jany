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