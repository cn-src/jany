import groovy.util.Node

plugins {
    `java-library`
    `maven-publish`
    jacoco
    id("com.github.ben-manes.versions")
    id("io.spring.dependency-management")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    registerFeature("optional") {
        usingSourceSet(sourceSets["main"])
    }
    withSourcesJar()
}

val springBootVersion: String by project
dependencies {
    api(platform(project(":jany-platform")))
    annotationProcessor(platform(project(":jany-platform")))
    testAnnotationProcessor(platform(project(":jany-platform")))

    compileOnly("org.jetbrains:annotations")
    compileOnly("org.projectlombok:lombok")
    compileOnly("com.google.code.findbugs:jsr305")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testCompileOnly("org.projectlombok:lombok")
    testCompileOnly("org.jetbrains:annotations")
    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.skyscreamer:jsonassert")
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
    generatedPomCustomization {
        enabled(false)
    }
}
tasks.named<Test>("test") {
    useJUnitPlatform()
    failFast = false
    jvmArgs = jvmArgs.apply { add("-Dorg.jooq.no-logo=true") }
}
tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
    }
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("jany")
                description.set("A Java library")
                url.set("https://github.com/cn-src/jany")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        name.set("cn-src")
                        email.set("public@javaer.cn")
                    }
                }
                scm {
                    connection.set("scm:git@github.com:cn-src/jany.git")
                    developerConnection.set("scm:git@github.com:cn-src/jany.git")
                    url.set("https://github.com/cn-src/jany.git")
                }
            }
            pom.withXml {
                @Suppress("UNCHECKED_CAST")
                (asNode().get("dependencyManagement") as List<Node>).firstOrNull().let {
                    asNode().remove(it)
                }
                @Suppress("UNCHECKED_CAST")
                val dependencies = (asNode().get("dependencies") as List<Node>?)?.firstOrNull()
                @Suppress("UNCHECKED_CAST")
                (dependencies?.children() as List<Node>?)?.filter {
                    "true" == (it.get("optional") as List<Node>?)?.firstOrNull()?.text()
                }?.forEach { dependencies?.remove(it) }
            }
        }
    }
}