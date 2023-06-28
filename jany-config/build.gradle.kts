plugins {
    id("jany-library")
}
val redissonVersion: String by project
dependencies {
    api("org.redisson:redisson-spring-boot-starter:$redissonVersion")

    testImplementation("com.redis.testcontainers:testcontainers-redis-junit:1.6.4")
}