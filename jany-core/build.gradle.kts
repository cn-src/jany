plugins {
    id("jany-library")
}
val hutoolVersion: String by project
val springdocVersion: String by project
dependencies {
    api("cn.hutool:hutool-core:$hutoolVersion")

    optionalApi("org.springdoc:springdoc-openapi-webmvc-core:$springdocVersion")
    compileOnly("com.fasterxml.jackson.core:jackson-annotations")

    testImplementation(project(":jany-test"))
}