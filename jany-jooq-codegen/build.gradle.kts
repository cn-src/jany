plugins {
    id("jany-library")
}
val jooqVersion: String by project
val handlebarsVersion: String by project
val classgraphVersion: String by project
val hutoolVersion: String by project

dependencies {
    implementation(project(":jany-jooq"))
    implementation("org.jooq:jooq-codegen:$jooqVersion")
    implementation("cn.hutool:hutool-core:$hutoolVersion")
    implementation("com.github.jknack:handlebars:$handlebarsVersion")
    implementation("io.github.classgraph:classgraph:$classgraphVersion")

    testImplementation("com.h2database:h2")
    testImplementation("org.postgresql:postgresql")
    testImplementation("org.springframework.data:spring-data-jdbc")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
}