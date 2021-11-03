plugins {
    id("jany-library")
}
val handlebarsVersion: String by project
dependencies {
    api("com.github.jknack:handlebars:$handlebarsVersion")

    optionalApi("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}