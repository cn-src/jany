plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.github.ben-manes:gradle-versions-plugin:0.39.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
}

repositories {
    gradlePluginPortal()
}