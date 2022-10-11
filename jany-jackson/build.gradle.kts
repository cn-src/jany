plugins {
    id("jany-library")
}
val jacksonVersion: String by project
dependencies {
    api(project(":jany-core"))
    api("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    optionalApi("org.jooq:jooq")

    testImplementation("com.h2database:h2")
}