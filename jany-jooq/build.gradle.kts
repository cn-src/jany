plugins {
    id("jany-library")
}
val jooqVersion = dependencyManagement.importedProperties["jooq.version"]

dependencies {
    api(project(":jany-core"))
    api(project(":jany-jackson"))
    api("org.jooq:jooq:$jooqVersion")

    optionalApi("org.springframework:spring-beans")
    optionalApi("org.postgresql:postgresql")

    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.data:spring-data-jdbc")
}