plugins {
    id("jany-library")
}

dependencies {
    api(project(":jany-core"))
    optionalApi(project(":jany-jackson"))

    optionalApi("com.yomahub:tlog-common")
    optionalApi("cn.hutool:hutool-extra")
    optionalApi("org.springframework.boot:spring-boot-starter-web")
    optionalApi("org.springframework.boot:spring-boot-starter-data-jdbc")
    optionalApi("org.springframework.boot:spring-boot-starter-jooq")
    optionalApi("org.springframework.boot:spring-boot-starter-security")
    optionalApi("org.springdoc:springdoc-openapi-webmvc-core")

    optionalApi("org.postgresql:postgresql")
    testImplementation("com.h2database:h2")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}