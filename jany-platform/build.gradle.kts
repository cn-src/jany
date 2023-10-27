plugins {
    `java-platform`
    `maven-publish`
    signing
}
val p = project
operator fun Project.get(prop: String) = project.property(prop)
dependencies {
    // bom
    api(platform("cn.hutool:hutool-bom:${p["hutoolVersion"]}"))
    api(platform("io.ebean:ebean-bom:${p["ebeanVersion"]}"))
    api(platform("cn.dev33:sa-token-bom:${p["saTokenVersion"]}"))
    api(platform("org.testcontainers:testcontainers-bom:${p["testcontainersVersion"]}"))
    api(platform("com.playtika.testcontainers:testcontainers-spring-boot-bom:${p["playtikaVersion"]}"))

    constraints {
//        api(project(":jany-archunit"))
        api(project(":jany-core"))
        api(project(":jany-ebean"))
        api(project(":jany-jackson"))
        api(project(":jany-jooq"))
        api(project(":jany-storage"))
        api(project(":jany-p6spy"))
        api(project(":jany-spring"))
        api(project(":jany-spring-boot-starter"))
//        api(project(":jany-spring-boot-starter-handlebars"))
//        api(project(":jany-spring-boot-starter-minio"))
        api(project(":jany-spring-boot-starter-p6spy"))
        api(project(":jany-spring-boot-starter-springdoc"))
        api(project(":jany-test"))
        api(project(":jany-validation"))

        // 注解库
        api("com.google.code.findbugs:jsr305:${p["jsr305Version"]}")
        api("org.jetbrains:annotations:${p["jetbrainsAnnotationsVersion"]}")

        // 模板引擎
        api("com.github.jknack:handlebars:${p["handlebarsVersion"]}")
        api("com.deepoove:poi-tl:${p["poiTlVersion"]}")
        api("com.alibaba:easyexcel:${p["easyexcelVersion"]}")
        api("com.alibaba:transmittable-thread-local:${p["transmittableThreadLocalVersion"]}")

        // 类信息扫描工具
        api("io.github.classgraph:classgraph:${p["classgraphVersion"]}")
        // SQL 日志统一输出工具
        api("p6spy:p6spy:${p["p6spyVersion"]}")
        // 对象存储服务-客户端
        api("io.minio:minio:${p["minioVersion"]}")
        // Java 架构检测框架
        api("com.tngtech.archunit:archunit-junit5:${p["archunitVersion"]}")
        // 日志追踪
        api("com.yomahub:tlog-common:${p["tlogVersion"]}")
        api("com.yomahub:tlog-web-spring-boot-starter:${p["tlogVersion"]}")

        api("io.github.sevdokimov.logviewer:log-viewer-spring-boot:${p["logviewerVersion"]}")
        api("org.redisson:redisson-spring-boot-starter:${p["redissonVersion"]}")
        runtime("com.taobao.arthas:arthas-spring-boot-starter:${p["arthasSpringBootVersion"]}")
        api("com.github.lianjiatech:retrofit-spring-boot-starter:${p["retrofitSpringBootVersion"]}")
        api("org.ssssssss:magic-api-spring-boot-starter:${p["magicApiVersion"]}")
        api("cloud.tianai.captcha:tianai-captcha-springboot-starter:${p["tianaiCaptchaVersion"]}")
        api("org.dromara.x-file-storage:x-file-storage-spring:${p["xFileStorageVersion"]}")
        api("org.lionsoul:ip2region:${p["ip2regionVersion"]}")

        // 工具库
        api("com.google.guava:guava:${p["guavaVersion"]}")
        api("cn.hutool:hutool-all:${p["hutoolVersion"]}")

        api("com.github.kagkarlsson:db-scheduler:${p["dbSchedulerVersion"]}")
        api("com.github.kagkarlsson:db-scheduler-spring-boot-starter:${p["dbSchedulerVersion"]}")

        api("com.github.binarywang:wx-java-channel-spring-boot-starter:${p["wxJavaVersion"]}")
        api("com.github.binarywang:wx-java-cp-spring-boot-starter:${p["wxJavaVersion"]}")
        api("com.github.binarywang:wx-java-miniapp-spring-boot-starter:${p["wxJavaVersion"]}")
        api("com.github.binarywang:wx-java-mp-spring-boot-starter:${p["wxJavaVersion"]}")
        api("com.github.binarywang:wx-java-open-spring-boot-starter:${p["wxJavaVersion"]}")
        api("com.github.binarywang:wx-java-pay-spring-boot-starter:${p["wxJavaVersion"]}")
        api("com.github.binarywang:wx-java-qidian-spring-boot-starter:${p["wxJavaVersion"]}")

        api("org.mapstruct:mapstruct:${p["mapstructVersion"]}")
        api("org.mapstruct:mapstruct-processor:${p["mapstructVersion"]}")

        api("com.github.gavlyukovskiy:p6spy-spring-boot-starter:${p["datasourceDecoratorVersion"]}")
        api("com.github.gavlyukovskiy:flexy-pool-spring-boot-starter:${p["datasourceDecoratorVersion"]}")

        api("org.springdoc:springdoc-openapi-starter-common:${p["springdocVersion"]}")
        api("org.springdoc:springdoc-openapi-starter-webmvc-ui:${p["springdocVersion"]}")
        api("org.springdoc:springdoc-openapi-starter-webmvc-api:${p["springdocVersion"]}")
        api("org.springdoc:springdoc-openapi-starter-webflux-ui:${p["springdocVersion"]}")
        api("org.springdoc:springdoc-openapi-starter-webflux-api:${p["springdocVersion"]}")

        api("de.codecentric:spring-boot-admin-server:${p["springBootAdminVersion"]}")
        api("de.codecentric:spring-boot-admin-server-ui:${p["springBootAdminVersion"]}")
        api("de.codecentric:spring-boot-admin-client:${p["springBootAdminVersion"]}")
        api("de.codecentric:spring-boot-admin-starter-client:${p["springBootAdminVersion"]}")
        api("de.codecentric:spring-boot-admin-starter-server:${p["springBootAdminVersion"]}")
        api("de.codecentric:spring-boot-admin-server-cloud:${p["springBootAdminVersion"]}")

        api("io.zonky.test:embedded-database-spring-test-autoconfigure:${p["zonkySpringVersion"]}")
        api("io.zonky.test:embedded-database-spring-test:${p["zonkySpringVersion"]}")
        api("io.zonky.test:embedded-postgres:${p["zonkyVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-bom:${p["zonkyPostgresVersion"]}")
    }
}
javaPlatform {
    allowDependencies()
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
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
        }
    }
}
signing {
    sign(publishing.publications["maven"])
}
tasks.withType<Sign>().configureEach {
    onlyIf { !version.toString().endsWith("SNAPSHOT") && project.hasProperty("signing.keyId") }
}