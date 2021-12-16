plugins {
    id("jany-library")
}

val minioVersion: String by project
dependencies {
    api("io.minio:minio:$minioVersion")
    api(project(":jany-minio"))

    optionalApi("org.springframework.boot:spring-boot-starter-web")
    optionalApi("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.1.0")
    testImplementation("com.playtika.testcontainers:embedded-minio")
}