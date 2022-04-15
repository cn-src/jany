package cn.javaer.jany.ebean.expression;

import cn.javaer.jany.util.AnnUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class QueryExprTest {
    @Test
    void name() throws Exception {
        final Field name = Demo.class.getField("name");
        final QueryExpr queryExpr = AnnUtils.findMergedAnnotation(name, QueryExpr.class);
        assertThat(queryExpr.property()).isEqualTo("name");
    }

    static class Demo {
        @QueryInRangeStart(property = "name")
        public String name;
    }
}