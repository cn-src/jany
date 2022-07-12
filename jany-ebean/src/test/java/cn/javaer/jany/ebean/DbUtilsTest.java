package cn.javaer.jany.ebean;

import io.ebean.Database;
import io.ebean.SqlRow;
import io.ebean.Transaction;
import io.zonky.test.db.postgres.junit5.EmbeddedPostgresExtension;
import io.zonky.test.db.postgres.junit5.SingleInstancePostgresExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author cn-src
 */
class DbUtilsTest {

    @RegisterExtension
    public SingleInstancePostgresExtension pg = EmbeddedPostgresExtension.singleInstance();

    @Test
    void upsert() {
        final Database db = EmbeddedPostgresUtils.create(pg);
        db.script().run("/DbUtilsTest.ddl");
        try (final Transaction tran = db.beginTransaction()) {
            DbUtils.insert(new InsertArgs().tableName("demo").rowList(Arrays.asList(
                Map.of("id", 1, "name", "name1", "created_date", LocalDateTime.now()),
                Map.of("id", 2, "name", "name2", "created_date", LocalDateTime.now()),
                Map.of("id", 3, "name", "name3", "created_date", LocalDateTime.now())
            )));
            tran.commit();
        }

        final List<SqlRow> rows = db.sqlQuery("SELECT * FROM demo").findList();
        Assertions.assertThat(rows).hasSize(3);
    }
}