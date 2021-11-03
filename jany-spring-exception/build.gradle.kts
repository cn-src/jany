plugins {
    id("jany-library")
}

dependencies {
    api(project(":jany-core"))
    optionalApi("org.springframework:spring-web")
    optionalApi("org.springframework:spring-context")
}