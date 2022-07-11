package cn.javaer.jany.ebean;

import cn.javaer.jany.ebean.query.QDemo;
import cn.javaer.jany.util.OptChain;
import org.junit.jupiter.api.Test;

/**
 * @author cn-src
 */
public class EbeanOptChainTest {
    @Test
    void testQBean() {
        OptChain.of(new QDemo())
            .opt(q -> q.id::eq, 1)
            .opt(q -> q.id::between, 1, 4)
            .fn(QDemo::or)
            .opt(q -> q.name::eq, "name")
            .fn(QDemo::endOr)
            .root().findList();
    }
}