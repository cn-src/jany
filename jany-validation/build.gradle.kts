plugins {
    id("jany-library")
}
val hutoolVersion: String by project
dependencies {
    api(project(":jany-core"))

    optionalApi("org.hibernate.validator:hibernate-validator")
    optionalApi("org.glassfish:jakarta.el")
    optionalApi("org.springframework:spring-webmvc")
}