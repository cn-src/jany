package cn.javaer.jany.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cn-src
 */
class AllOrNoneEmptyValidatorTest {
    private static Validator validator;

    @BeforeAll
    static void testInitialize() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("异常情况")
    void testError() {
        final Set<ConstraintViolation<Demo>> vs1 = validator.validate(new Demo("f1", 1, null));
        assertEquals(1, vs1.size());

        final Set<ConstraintViolation<Demo>> vs2 = validator.validate(
            new Demo("f1", null, new String[]{"s"}));
        assertEquals(1, vs2.size());

        final Set<ConstraintViolation<Demo>> vs3 = validator.validate(new Demo("f1", null, null));
        assertEquals(2, vs3.size());
    }

    @Test
    @DisplayName("字段全部不为空")
    void testAllEmpty() {
        final Set<ConstraintViolation<Demo>> vs = validator.validate(new Demo(null, null, null));
        assertEquals(0, vs.size());
    }

    @Test
    @DisplayName("字段全部为空")
    void testNoneEmpty() {
        final Set<ConstraintViolation<Demo>> vs = validator.validate(
            new Demo("f1", 1, new String[]{"s"}));
        assertEquals(0, vs.size());
    }

    @AllOrNoneEmpty({Demo.Fields.field1, Demo.Fields.field2,})
    @AllOrNoneEmpty({Demo.Fields.field1, Demo.Fields.field3})
    static class Demo {
        private String field1;
        private Integer field2;
        private String[] field3;

        public Demo(String field1, Integer field2, String[] field3) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
        }

        public String getField1() {
            return this.field1;
        }

        public Integer getField2() {
            return this.field2;
        }

        public String[] getField3() {
            return this.field3;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public void setField2(Integer field2) {
            this.field2 = field2;
        }

        public void setField3(String[] field3) {
            this.field3 = field3;
        }

        public static final class Fields {
            public static final String field1 = "field1";
            public static final String field2 = "field2";
            public static final String field3 = "field3";
        }
    }
}