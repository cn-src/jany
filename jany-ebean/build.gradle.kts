plugins {
    id("jany-library")
    id("io.ebean") version "13.5.0"
}

val ebeanVersion: String by project
dependencies {
    api(project(":jany-core"))
    api("io.ebean:ebean:$ebeanVersion")

    testImplementation("io.ebean:ebean-test")
    testImplementation("com.h2database:h2")
    testImplementation(project(":jany-test"))
    testAnnotationProcessor("io.ebean:querybean-generator")
}