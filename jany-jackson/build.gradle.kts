plugins {
    id("jany-library")
}
dependencies {
    api(project(":jany-core"))

    api("com.fasterxml.jackson.core:jackson-databind")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    optionalApi("org.jooq:jooq")

    testImplementation("com.h2database:h2")
}