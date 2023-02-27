plugins {
    id("jany-library")
    id("io.ebean") version "13.13.2"
}

val ebeanVersion: String by project
dependencies {
    api(project(":jany-core"))
    api("io.ebean:ebean:$ebeanVersion")

    optionalApi("cn.hutool:hutool-extra")
    optionalApi("org.springframework:spring-context")

    testImplementation("io.ebean:ebean-test")
    testImplementation("com.h2database:h2")
    testImplementation(project(":jany-test"))
    testAnnotationProcessor("io.ebean:querybean-generator")
    testImplementation("io.zonky.test:embedded-postgres")
    testImplementation("org.slf4j:slf4j-simple")
}