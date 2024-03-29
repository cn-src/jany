plugins {
    id("jany-library")
}
dependencies {
    api(project(":jany-core"))
    api(project(":jany-spring"))
    api(project(":jany-jackson"))

    optionalApi("org.jooq:jooq")
    optionalApi("org.springframework.boot:spring-boot-starter-web")

    testImplementation("com.h2database:h2")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}