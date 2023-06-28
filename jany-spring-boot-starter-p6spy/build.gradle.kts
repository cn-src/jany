plugins {
    id("jany-library")
}
val datasourceDecoratorVersion: String by project
dependencies {
    api(project(":jany-p6spy"))
    api("com.github.gavlyukovskiy:p6spy-spring-boot-starter:$datasourceDecoratorVersion")

    optionalApi("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}