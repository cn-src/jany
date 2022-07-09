plugins {
    id("jany-library")
}
dependencies {
    api(project(":jany-p6spy"))

    optionalApi("io.ebean:ebean")
    optionalApi("com.github.gavlyukovskiy:p6spy-spring-boot-starter")
    optionalApi("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}