import java.io.FileInputStream
import java.util.*

plugins {
    id("java-library")
    id("com.github.ben-manes.versions") version "0.47.0"
    id("io.spring.dependency-management") version "1.1.0"
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
    // 注解库
    api("com.google.code.findbugs:jsr305:${p["jsr305Version"]}")
    api("org.jetbrains:annotations:${p["jetbrainsAnnotationsVersion"]}")

    // 序列化
    api("com.fasterxml.jackson.datatype:jackson-datatype-eclipse-collections:${p["jacksonDatatypesCollectionsVersion"]}")

    // 模板引擎
    api("com.github.jknack:handlebars:${p["handlebarsVersion"]}")
    api("com.deepoove:poi-tl:${p["poiTlVersion"]}")
    api("com.alibaba:easyexcel:${p["easyexcelVersion"]}")
    api("com.alibaba:transmittable-thread-local:${p["transmittableThreadLocalVersion"]}")

    // 类信息扫描工具
    api("io.github.classgraph:classgraph:${p["classgraphVersion"]}")
    // SQL 日志统一输出工具
    api("p6spy:p6spy:${p["p6spyVersion"]}")
    // 集合查询引擎
    api("com.googlecode.cqengine:cqengine:${p["cqengineVersion"]}")
    // 对象存储服务-客户端
    api("io.minio:minio:${p["minioVersion"]}")
    // Java 架构检测框架
    api("com.tngtech.archunit:archunit-junit5:${p["archunitVersion"]}")
    // web 反向代理
    api("com.github.mkopylec:charon-spring-webmvc:${p["charonVersion"]}")
    api("com.github.mkopylec:charon-spring-webflux:${p["charonVersion"]}")
    // 日志追踪
    api("com.yomahub:tlog-common:${p["tlogVersion"]}")
    api("com.yomahub:tlog-web-spring-boot-starter:${p["tlogVersion"]}")

    api("io.github.sevdokimov.logviewer:log-viewer-spring-boot:${p["logviewerVersion"]}")
    api("org.redisson:redisson-spring-boot-starter:${p["redissonVersion"]}")
    api("com.taobao.arthas:arthas-spring-boot-starter:${p["arthasSpringBootVersion"]}")
    api("com.baomidou:dynamic-datasource-spring-boot-starter:${p["dynamicDatasourceVersion"]}")
    api("com.github.lianjiatech:retrofit-spring-boot-starter:${p["retrofitSpringBootVersion"]}")
    api("org.ssssssss:magic-api-spring-boot-starter:${p["magicApiVersion"]}")
    api("cloud.tianai.captcha:tianai-captcha-springboot-starter:${p["tianaiCaptchaVersion"]}")
    api("cn.xuyanwu:spring-file-storage:${p["springFileStorageVersion"]}")
    api("org.lionsoul:ip2region:${p["ip2regionVersion"]}")

    // 工具库
    api("com.google.guava:guava:${p["guavaVersion"]}")
    api("org.eclipse.collections:eclipse-collections:${p["eclipseCollectionsVersion"]}")
    api("org.apache.commons:commons-collections4:${p["commonsCollections4Version"]}")
    api("commons-io:commons-io:${p["commonsIoVersion"]}")
    api("org.apache.commons:commons-lang3:${p["commonsLang3Version"]}")

    // bom
    api("cn.hutool:hutool-bom:${p["hutoolVersion"]}")
    api("io.ebean:ebean-bom:${p["ebeanVersion"]}")
    api("org.testcontainers:testcontainers-bom:${p["playtikaVersion"]}")
    api("com.playtika.testcontainers:testcontainers-spring-boot-bom:${p["testcontainersVersion"]}")


    api("com.github.kagkarlsson:db-scheduler:${p["dbSchedulerVersion"]}")
    api("com.github.kagkarlsson:db-scheduler-spring-boot-starter:${p["dbSchedulerVersion"]}")

    api("com.github.binarywang:wx-java-channel-spring-boot-starter:${p["wxJavaVersion"]}")
    api("com.github.binarywang:wx-java-cp-spring-boot-starter:${p["wxJavaVersion"]}")
    api("com.github.binarywang:wx-java-miniapp-spring-boot-starter:${p["wxJavaVersion"]}")
    api("com.github.binarywang:wx-java-mp-spring-boot-starter:${p["wxJavaVersion"]}")
    api("com.github.binarywang:wx-java-open-spring-boot-starter:${p["wxJavaVersion"]}")
    api("com.github.binarywang:wx-java-pay-spring-boot-starter:${p["wxJavaVersion"]}")
    api("com.github.binarywang:wx-java-qidian-spring-boot-starter:${p["wxJavaVersion"]}")

    api("org.mybatis:mybatis:${p["mybatisVersion"]}")
    api("org.mybatis:mybatis-spring:${p["mybatisSpringVersion"]}")
    api("org.mybatis.dynamic-sql:mybatis-dynamic-sql:${p["mybatisDynamicSqlVersion"]}")
    api("org.mybatis.caches:mybatis-ehcache:${p["mybatisEhcacheVersion"]}")
    api("org.mybatis.spring.boot:mybatis-spring-boot-starter:${p["mybatisSpringBootVersion"]}")
    api("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:${p["mybatisSpringBootVersion"]}")

    api("cn.dev33:sa-token-core:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-alone-redis:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-dao-redis:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-dao-redis-jackson:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-dialect-thymeleaf:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-oauth2:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-quick-login:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-spring-aop:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-temp-jwt:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-jwt:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-context-dubbo:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-servlet:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-spring-boot-starter:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-reactor-spring-boot-starter:${p["saTokenVersion"]}")
    api("cn.dev33:sa-token-solon-plugin:${p["saTokenVersion"]}")

    api("org.mapstruct:mapstruct:${p["mapstructVersion"]}")
    api("org.mapstruct:mapstruct-processor:${p["mapstructVersion"]}")

    api("org.apache.shardingsphere:shardingsphere-jdbc-core:${p["shardingsphereVersion"]}")
    api("org.apache.shardingsphere.elasticjob:elasticjob-lite-core:${p["elasticjobVersion"]}")

    api("com.github.gavlyukovskiy:p6spy-spring-boot-starter:${p["datasourceDecoratorVersion"]}")
    api("com.github.gavlyukovskiy:flexy-pool-spring-boot-starter:${p["datasourceDecoratorVersion"]}")

    api("org.springdoc:springdoc-openapi-starter-webmvc-ui:${p["springdocVersion"]}")
    api("org.springdoc:springdoc-openapi-starter-webmvc-api:${p["springdocVersion"]}")
    api("org.springdoc:springdoc-openapi-starter-webflux-ui:${p["springdocVersion"]}")
    api("org.springdoc:springdoc-openapi-starter-webflux-api:${p["springdocVersion"]}")

    api("io.vavr:vavr:${p["vavrVersion"]}")
    api("io.vavr:vavr-jackson:${p["vavrJacksonVersion"]}")
    api("io.vavr:vavr-test:${p["vavrVersion"]}")
    api("io.vavr:vavr-match:${p["vavrVersion"]}")

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