package cn.javaer.jany.ebean;

import cn.javaer.jany.model.Page;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.model.Sort;
import cn.javaer.jany.test.JsonAssert;
import cn.javaer.jany.test.Log;
import io.ebean.DB;
import io.ebean.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class BaseFinderTest {
    Database db = DB.getDefault();

    @BeforeEach
    void setUp() {
        db.truncate("DEMO");
    }

    @Test
    void allSort() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final List<Demo> demos = Demo.find.list(Sort.DEFAULT);
        JsonAssert.assertEqualsAndOrder("BaseFinderTest.allSort.out.json", Log.json(demos));
    }

    @Test
    void page() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final Page<Demo> demos = Demo.find.page(PageParam.of(1, 2));
        JsonAssert.assertEqualsAndOrder("BaseFinderTest.pagedSort.out.json", Log.json(demos));
    }

    @Test
    void pageExample() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final Page<Demo> demos = Demo.find.page(new DemoExample().setName("name2"),
            PageParam.of(1, 2));
        assertThat(demos.getTotal()).isEqualTo(1);
        assertThat(demos.getContent().get(0).getName()).isEqualTo("name2");
    }
}