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

    private Database db;

    @Test
    void insert() {
        db = EmbeddedPostgresUtils.create(pg);
        db.script().run("/DbUtilsTest.ddl");
        db.truncate("demo");

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

    @Test
    void update() {
        db = EmbeddedPostgresUtils.create(pg);
        db.script().run("/DbUtilsTest.ddl");
        db.truncate("demo");

        try (final Transaction tran = db.beginTransaction()) {
            DbUtils.insert(new InsertArgs().tableName("demo").rowList(Arrays.asList(
                Map.of("id", 1, "name", "name1", "created_date", LocalDateTime.now()),
                Map.of("id", 2, "name", "name2", "created_date", LocalDateTime.now()),
                Map.of("id", 3, "name", "name3", "created_date", LocalDateTime.now())
            )));
            tran.commit();
        }
        try (final Transaction tran = db.beginTransaction()) {
            DbUtils.update(new UpdateArgs().tableName("demo").updateKey("id").rowList(Arrays.asList(
                Map.of("id", 1, "name", "name1_updated", "created_date", LocalDateTime.now()),
                Map.of("id", 2, "name", "name2_updated", "created_date", LocalDateTime.now()),
                Map.of("id", 3, "name", "name3_updated", "created_date", LocalDateTime.now())
            )));
            tran.commit();
        }
        final List<SqlRow> rows = db.sqlQuery("SELECT * FROM demo").findList();
        Assertions.assertThat(rows).hasSize(3);
        Assertions.assertThat(rows.get(0).getString("name")).isEqualTo("name1_updated");
        Assertions.assertThat(rows.get(1).getString("name")).isEqualTo("name2_updated");
        Assertions.assertThat(rows.get(2).getString("name")).isEqualTo("name3_updated");
    }
}