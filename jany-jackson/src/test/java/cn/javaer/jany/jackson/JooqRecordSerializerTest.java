// package cn.javaer.jany.jackson;
//
// import org.jooq.DSLContext;
// import org.jooq.Table;
// import org.jooq.impl.DSL;
// import org.jooq.impl.SQLDataType;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
//
// import java.util.UUID;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
// /**
//  * @author cn-src
//  */
// class JooqRecordSerializerTest {
//
//     DSLContext dsl;
//
//     Table<?> table = DSL.table("demo");
//
//     @BeforeEach
//     void setUp() {
//         this.dsl = DSL.using("jdbc:h2:mem:" + UUID.randomUUID(), "sa", "");
//         this.dsl.createTable(this.table)
//             .column(DSL.field("col_c1", SQLDataType.VARCHAR(50)))
//             .column(DSL.field("col_c2", SQLDataType.VARCHAR(50)))
//             .execute();
//         this.dsl.insertInto(this.table).columns(DSL.field("col_c1"), DSL.field("col_c2"))
//             .values("v1", "v2")
//             .execute();
//     }
//
//     @Test
//     void serialize() {
//         final String json = Json.DEFAULT.write(this.dsl.selectFrom(this.table).fetch());
//         assertThat(json).isEqualTo("[{\"colC1\":\"v1\",\"colC2\":\"v2\"}]");
//     }
// }