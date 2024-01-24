import java.io.FileInputStream
import java.util.*

plugins {
    id("java-library")
    id("com.github.ben-manes.versions") version "0.51.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "cn.javaer.jany"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val p = Properties()
val input = FileInputStream(file("../gradle.properties"))
p.load(input)
input.close()
dependencies {
    api(platform("org.dromara.hutool:hutool-bom:${p["hutoolVersion"]}"))
    api(platform("io.ebean:ebean-bom:${p["ebeanVersion"]}"))
    api(platform("cn.dev33:sa-token-bom:${p["saTokenVersion"]}"))
    api(platform("org.testcontainers:testcontainers-bom:${p["testcontainersVersion"]}"))
    api(platform("com.playtika.testcontainers:testcontainers-spring-boot-bom:${p["playtikaVersion"]}"))

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
    api("com.taobao.arthas:arthas-spring-boot-starter:${p["arthasSpringBootVersion"]}")
    api("com.github.lianjiatech:retrofit-spring-boot-starter:${p["retrofitSpringBootVersion"]}")
    api("org.ssssssss:magic-api-spring-boot-starter:${p["magicApiVersion"]}")
    api("cloud.tianai.captcha:tianai-captcha-springboot-starter:${p["tianaiCaptchaVersion"]}")
    api("org.dromara.x-file-storage:x-file-storage-spring:${p["xFileStorageVersion"]}")
    api("org.lionsoul:ip2region:${p["ip2regionVersion"]}")

    // 工具库
    api("com.google.guava:guava:${p["guavaVersion"]}")
    api("org.dromara.hutool:hutool-all:${p["hutoolVersion"]}")

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
dependencyManagement {
    imports {
        mavenBom("io.zonky.test.postgres:embedded-postgres-binaries-bom:${p["zonkyPostgresVersion"]}")
    }
}