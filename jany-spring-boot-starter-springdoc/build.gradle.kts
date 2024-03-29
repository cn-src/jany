plugins {
    id("jany-library")
}
val springdocVersion: String by project
dependencies {
    api(project(":jany-spring-boot-starter"))

    optionalApi("org.springdoc:springdoc-openapi-starter-webmvc-api:$springdocVersion")
    optionalApi("org.springframework.boot:spring-boot-starter-data-jdbc")
    optionalApi("org.springframework.boot:spring-boot-starter-data-rest")
    optionalApi("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}