package cn.javaer.jany.ebean.expression;

import cn.javaer.jany.util.AnnotationUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class WhereExpressionTest {
    @Test
    void name() throws Exception {
        final Field name = Demo.class.getField("name");
        final WhereExpression whereExpression = AnnotationUtils.findMergedAnnotation(WhereExpression.class, name).get();
        assertThat(whereExpression.property()).isEqualTo("name");
    }

    static class Demo {
        @WhereInRangeStart(property = "name")
        public String name;
    }
}