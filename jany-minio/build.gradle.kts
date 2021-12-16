plugins {
    id("jany-library")
}

val minioVersion: String by project
val springdocVersion: String by project

dependencies {
    api("io.minio:minio:$minioVersion")

    optionalApi("org.springdoc:springdoc-openapi-webmvc-core:$springdocVersion")
}