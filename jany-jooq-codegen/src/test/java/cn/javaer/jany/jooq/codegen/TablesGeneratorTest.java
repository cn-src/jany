package cn.javaer.jany.jooq.codegen;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;
import org.jooq.meta.postgres.PostgresDatabase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author cn-src
 */
@Disabled
@Testcontainers
class TablesGeneratorTest {
    @Container
    private final PostgreSQLContainer<?> container =
        new PostgreSQLContainer<>(DockerImageName.parse("postgis/postgis:10-2.5-alpine")
            .asCompatibleSubstituteFor("postgres"))
            .withDatabaseName(TablesGeneratorTest.class.getSimpleName());

    @Test
    void generateTable() throws Exception {
        final DataSourceInfo dataSourceInfo = TestContainer.createDataSourceInfo(this.container);
        final DataSource dataSource = TestContainer.createDataSource(dataSourceInfo);
        try (final Connection conn = dataSource.getConnection()) {
            conn.prepareStatement("CREATE TABLE demo\n" +
                "(\n" +
                "    " +
                "id           bigserial NOT NULL\n" +
                "       " +
                " CONSTRAINT demo_pkey" +
                " PRIMARY KEY,\n" +
                "    geom1        geometry(Polygon, 4326),\n" +
                "    geom2        geometry(Polygon, 4326),\n" +
                "    jsonb1       jsonb,\n" +
                "    jsonb2       jsonb,\n" +
                "    created_time timestamp\n" +
                ");").execute();
        }

        final Configuration configuration = new Configuration()
            .withJdbc(new Jdbc()
                .withDriver("org.postgresql.Driver")
                .withUrl(dataSourceInfo.getJdbcUrl())
                .withUser(dataSourceInfo.getUsername())
                .withPassword(dataSourceInfo.getPassword())
            )
            .withGenerator(new Generator()
                .withName(TablesGenerator.class.getName())
                .withDatabase(new Database()
                    .withName(PostgresDatabase.class.getName())
                    .withInputSchema("public")
                    .withIncludes(".*")
                    .withExcludes("spatial_ref_sys|geography_columns|geometry_columns" +
                        "|raster_columns|raster_overviews")
                )
                .withTarget(new Target()
                    .withClean(true)
                    .withPackageName("test.gen")
                    .withDirectory(System.getProperty("user.dir") + "/src/test/java")
                )
            );
        GenerationTool.generate(configuration);
    }
}