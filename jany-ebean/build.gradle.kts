plugins {
    id("jany-library")
    id("io.ebean") version "13.6.5"
}

val ebeanVersion: String by project
dependencies {
    api(project(":jany-core"))
    api("io.ebean:ebean:$ebeanVersion")

    testImplementation("io.ebean:ebean-test")
    testImplementation("com.h2database:h2")
    testImplementation(project(":jany-test"))
    testAnnotationProcessor("io.ebean:querybean-generator")
    testImplementation("io.zonky.test:embedded-postgres")
    testImplementation("org.slf4j:slf4j-simple")
}