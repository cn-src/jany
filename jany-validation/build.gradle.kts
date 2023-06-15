plugins {
    id("jany-library")
}
val hutoolVersion: String by project
dependencies {
    api(project(":jany-core"))

    optionalApi("org.springframework.boot:spring-boot-starter-web")
    optionalApi("org.springframework.boot:spring-boot-starter-validation")
}