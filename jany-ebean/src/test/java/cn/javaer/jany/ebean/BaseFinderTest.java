package cn.javaer.jany.ebean;

import cn.javaer.jany.model.Page;
import cn.javaer.jany.model.PageParam;
import cn.javaer.jany.test.JsonAssert;
import cn.javaer.jany.test.Log;
import io.ebean.DB;
import io.ebean.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author cn-src
 */
class BaseFinderTest {
    Database db = DB.getDefault();

    @BeforeEach
    void setUp() {
        db.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        db.rollbackTransaction();
    }

    @Test
    void allSort() {
        db.script().run("/BaseFinderTest.allSort.in.sql");
        final List<Demo> demos = Demo.find.allSorted();
        JsonAssert.assertEqualsAndOrder("BaseFinderTest.allSort.out.json", Log.json(demos));
    }

    @Test
    void pagedSort() {
//        db.script().run("/BaseFinderTest.allSort.in.sql");
        final Page<Demo> demos = Demo.find.pagedSorted(PageParam.of(1, 2));
        JsonAssert.assertEqualsAndOrder("BaseFinderTest.pagedSort.out.json", Log.json(demos));
    }
}