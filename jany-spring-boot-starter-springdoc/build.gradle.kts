plugins {
    id("jany-library")
}
val springdocVersion: String by project
dependencies {
    api(project(":jany-spring-boot-starter"))
    api("org.springdoc:springdoc-openapi-starter-common:$springdocVersion")

//    optionalApi("org.springdoc:springdoc-openapi-data-rest:$springdocVersion")
//    optionalApi("org.springframework.boot:spring-boot-starter-data-jdbc")
    optionalApi("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}