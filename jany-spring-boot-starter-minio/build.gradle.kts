plugins {
    id("jany-library")
}

val minioVersion: String by project
dependencies {
    api("io.minio:minio:$minioVersion")

    optionalApi("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}