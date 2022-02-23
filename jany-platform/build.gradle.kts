plugins {
    `java-platform`
    `maven-publish`
    signing
}
val p = project
operator fun Project.get(prop: String) = project.property(prop)
dependencies {
    constraints {
        api(project(":jany-archunit"))
        api(project(":jany-core"))
        api(project(":jany-ebean"))
        api(project(":jany-jackson"))
        api(project(":jany-jooq"))
        api(project(":jany-jooq-codegen"))
        api(project(":jany-minio"))
        api(project(":jany-p6spy"))
        api(project(":jany-spring"))
        api(project(":jany-spring-boot-starter"))
        api(project(":jany-spring-boot-starter-handlebars"))
        api(project(":jany-spring-boot-starter-minio"))
        api(project(":jany-spring-boot-starter-p6spy"))
        api(project(":jany-spring-boot-starter-springdoc"))
        api(project(":jany-test"))
        api(project(":jany-validation"))

        // 工具库
        api("com.google.guava:guava:${p["guavaVersion"]}")
        api("org.eclipse.collections:eclipse-collections:${p["eclipseCollectionsVersion"]}")
        api("org.apache.commons:commons-collections4:${p["commonsCollections4Version"]}")
        api("commons-io:commons-io:${p["commonsIoVersion"]}")
        api("org.apache.commons:commons-lang3:${p["commonsLang3Version"]}")

        api("cn.hutool:hutool-all:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-core:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-aop:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-bloomFilter:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-cache:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-crypto:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-db:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-dfa:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-extra:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-http:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-log:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-script:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-setting:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-system:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-cron:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-json:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-poi:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-captcha:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-socket:${p["hutoolVersion"]}")
        api("cn.hutool:hutool-jwt:${p["hutoolVersion"]}")

        api("io.ebean:ebean-ddl-runner:${p["ebeanDdlRunnerVersion"]}")
        api("io.ebean:ebean-migration-auto:${p["ebeanMigrationAutoVersion"]}")
        api("io.ebean:ebean-migration:${p["ebeanMigrationVersion"]}")
        api("io.ebean:ebean-datasource-api:${p["ebeanDatasourceVersion"]}")
        api("io.ebean:ebean-datasource:${p["ebeanDatasourceVersion"]}")
        api("io.ebean:ebean-agent:${p["ebeanAgentVersion"]}")
        api("io.ebean:ebean-maven-plugin:${p["ebeanMavenPluginVersion"]}")
        api("io.ebean:ebean-test-docker:${p["ebeanTestDockerVersion"]}")
        api("io.ebean:ebean:${p["ebeanVersion"]}")
        api("io.ebean:ebean-annotation:${p["ebeanAnnotation"]}")
        api("io.ebean:ebean-api:${p["ebeanVersion"]}")
        api("io.ebean:ebean-core:${p["ebeanVersion"]}")
        api("io.ebean:ebean-core-type:${p["ebeanVersion"]}")
        api("io.ebean:ebean-ddl-generator:${p["ebeanVersion"]}")
        api("io.ebean:ebean-externalmapping-api:${p["ebeanVersion"]}")
        api("io.ebean:ebean-externalmapping-xml:${p["ebeanVersion"]}")
        api("io.ebean:ebean-autotune:${p["ebeanVersion"]}")
        api("io.ebean:ebean-querybean:${p["ebeanVersion"]}")
        api("io.ebean:querybean-generator:${p["ebeanVersion"]}")
        api("io.ebean:kotlin-querybean-generator:${p["ebeanVersion"]}")
        api("io.ebean:ebean-test:${p["ebeanVersion"]}")
        api("io.ebean:ebean-postgis:${p["ebeanVersion"]}")
        api("io.ebean:ebean-redis:${p["ebeanVersion"]}")

        // 注解库
        api("com.google.code.findbugs:jsr305:${p["jsr305Version"]}")
        api("org.jetbrains:annotations:${p["jetbrainsAnnotationsVersion"]}")

        // 序列化
        api("com.fasterxml.jackson.datatype:jackson-datatype-eclipse-collections:${p["jacksonDatatypesCollectionsVersion"]}")

        // 模板引擎
        api("com.github.jknack:handlebars:${p["handlebarsVersion"]}")
        api("com.deepoove:poi-tl:${p["poiTlVersion"]}")

        // 类信息扫描工具
        api("io.github.classgraph:classgraph:${p["classgraphVersion"]}")

        api("com.alibaba:easyexcel:${p["easyexcelVersion"]}")

        // SQL 日志统一输出工具
        api("p6spy:p6spy:${p["p6spyVersion"]}")

        // 集合查询引擎
        api("com.googlecode.cqengine:cqengine:${p["cqengineVersion"]}")

        // 对象存储服务-客户端
        api("io.minio:minio:${p["minioVersion"]}")

        // Java 架构检测框架
        api("com.tngtech.archunit:archunit-junit5:${p["archunitVersion"]}")

        api("com.yomahub:tlog-common:${p["tlogVersion"]}")
        api("com.yomahub:tlog-web-spring-boot-starter:${p["tlogVersion"]}")

        api("org.mybatis:mybatis:${p["mybatisVersion"]}")
        api("org.mybatis:mybatis-spring:${p["mybatisSpringVersion"]}")
        api("org.mybatis.dynamic-sql:mybatis-dynamic-sql:${p["mybatisDynamicSqlVersion"]}")
        api("org.mybatis.caches:mybatis-ehcache:${p["mybatisEhcacheVersion"]}")
        api("org.mybatis.spring.boot:mybatis-spring-boot-starter:${p["mybatisSpringBootVersion"]}")
        api("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:${p["mybatisSpringBootVersion"]}")

        // web 反向代理
        api("com.github.mkopylec:charon-spring-webmvc:${p["charonVersion"]}")
        api("com.github.mkopylec:charon-spring-webflux:${p["charonVersion"]}")

        runtime("com.taobao.arthas:arthas-spring-boot-starter:${p["arthasSpringBootVersion"]}")
        api("com.baomidou:dynamic-datasource-spring-boot-starter:${p["dynamicDatasourceVersion"]}")
        api("com.github.lianjiatech:retrofit-spring-boot-starter:${p["retrofitSpringBootVersion"]}")
        api("org.ssssssss:magic-api-spring-boot-starter:${p["magicApiVersion"]}")

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

        api("org.springdoc:springdoc-openapi-ui:${p["springdocVersion"]}")
        api("org.springdoc:springdoc-openapi-webmvc-core:${p["springdocVersion"]}")
        api("org.springdoc:springdoc-openapi-security:${p["springdocVersion"]}")
        api("org.springdoc:springdoc-openapi-data-rest:${p["springdocVersion"]}")

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

        api("org.testcontainers:postgresql:${p["testcontainersVersion"]}")
        api("org.testcontainers:mysql:${p["testcontainersVersion"]}")
        api("org.testcontainers:mariadb:${p["testcontainersVersion"]}")
        api("org.testcontainers:oracle-x:${p["testcontainersVersion"]}")
        api("org.testcontainers:elasticsearch:${p["testcontainersVersion"]}")
        api("org.testcontainers:spock:${p["testcontainersVersion"]}")
        api("org.testcontainers:junit-jupiter:${p["testcontainersVersion"]}")

        // testcontainers 的 SpringBoot 集成
        api("com.playtika.testcontainers:testcontainers-common:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-aerospike:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-couchbase:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-dynamodb:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-elasticsearch:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-google-pubsub:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-influxdb:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-kafka:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-keycloak:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-mariadb:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-memsql:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-mongodb:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-neo4j:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-oracle-xe:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-postgresql:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-rabbitmq:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-redis:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-vault:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-voltdb:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-mysql:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-minio:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-localstack:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-clickhouse:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-cassandra:${p["playtikaVersion"]}")
        api("com.playtika.testcontainers:embedded-selenium:${p["playtikaVersion"]}")

        api("io.zonky.test:embedded-database-spring-test-autoconfigure:${p["zonkySpringVersion"]}")
        api("io.zonky.test:embedded-database-spring-test:${p["zonkySpringVersion"]}")
        api("io.zonky.test:embedded-postgres:${p["zonkyVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-darwin-amd64:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-amd64:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-amd64-alpine:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-amd64-alpine-lite:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-windows-amd64:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-i386:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-i386-alpine:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-i386-alpine-lite:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-arm32v6:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-arm32v6-alpine:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-arm32v6-alpine-lite:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-arm32v7:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-arm64v8:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-arm64v8-alpine:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-arm64v8-alpine-lite:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-ppc64le:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-ppc64le-alpine:${p["zonkyPostgresVersion"]}")
        api("io.zonky.test.postgres:embedded-postgres-binaries-linux-ppc64le-alpine-lite:${p["zonkyPostgresVersion"]}")
    }
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