package cn.javaer.jany.jooq;

import cn.javaer.jany.jooq.field.JsonbField;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cn-src
 */
class PGDSLTest {

    DSLContext dsl = DSL.using(SQLDialect.POSTGRES);

    @Test
    void stContains() {

        final Field<Boolean> stContains = PGDSL.stContains(DSL.field("geom_a", PGDSL.GEOMETRY_TYPE),
            DSL.field("geom_b", PGDSL.GEOMETRY_TYPE));
        assertThat(this.dsl.renderInlined(stContains))
            .isEqualTo("ST_Contains(geom_a, geom_b)");
    }

    @Test
    void stAsGeoJSON() {
        final Field<String> stAsGeoJSON = PGDSL.stAsGeoJson(DSL.field("geom", PGDSL.GEOMETRY_TYPE));
        assertThat(this.dsl.renderInlined(stAsGeoJSON))
            .isEqualTo("ST_AsGeoJSON(geom)");
    }

    // @Test
    // void jsonbObjectAgg2() {
    //     final JsonbField<Record, JSONB> jsonbField =
    //         PGDSL.jsonbObjectAgg(new Field[]{
    //             DSL.field("f1", String.class),
    //             DSL.field("f2", String.class)}, '-',
    //             DSL.field("v1", String.class));
    //     assertThat(this.dsl.renderInlined(jsonbField))
    //         .isEqualTo("jsonb_object_agg((f1 || '-' || f2),v1)");
    // }
}