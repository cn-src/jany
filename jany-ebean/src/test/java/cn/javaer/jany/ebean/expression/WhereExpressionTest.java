package cn.javaer.jany.ebean.expression;

import cn.javaer.jany.util.AnnUtils;
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
        final WhereExpression whereExpression = AnnUtils.findMergedAnnotation(name,
            WhereExpression.class);
        assertThat(whereExpression.property()).isEqualTo("name");
    }

    static class Demo {
        @WhereInRangeStart(property = "name")
        public String name;
    }
}