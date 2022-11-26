package cn.javaer.jany.ebean;

import cn.javaer.jany.ebean.query.QDemo;
import io.ebean.DB;
import io.ebean.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class QryTest {

    Database db = DB.byName("db");

    @BeforeEach
    void setUp() {
        db.truncate("DEMO");
    }

    @Test
    void build() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final QDemo qDemo = new QDemo(DB.byName("db"));
        final List<Demo> list = Qry.of(qDemo).opt(qDemo.id::eq, 1L).list();
        assertThat(list).hasSize(1);

        final List<Demo> list2 = Qry.of(qDemo)
            .opt(qDemo.id::eq, 1L)
            .opt(qDemo.createdDate::between, LocalDateTime.now(), LocalDateTime.now())
            .list();
        assertThat(list2).isEmpty();
    }

    @Test
    void orTest() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final QDemo q = new QDemo(DB.byName("db"));
        final List<Demo> list = Qry.of(q)
            .fn(q::or)
            .opt(q.id::eq, 1L)
            .opt(q.id::eq, (Long) null)
            .fn(q::endOr)
            .list();
        assertThat(list).hasSize(1);
    }

    @Test
    void orTest2() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final QDemo q = new QDemo(DB.byName("db"));
        final List<Demo> list = Qry.of(q)
            .fn(() -> q.where().or().query())
            .opt(q.id::eq, 1L)
            .opt(q.id::eq, (Long) null)
            .list();
        assertThat(list).hasSize(1);
    }
}