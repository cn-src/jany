plugins {
    id("jany-library")
}
val hutoolVersion: String by project
dependencies {
    implementation("cn.hutool:hutool-core:$hutoolVersion")

    optionalApi("org.hibernate.validator:hibernate-validator")
    optionalApi("org.glassfish:jakarta.el")
}