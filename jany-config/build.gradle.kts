plugins {
    id("jany-library")
}

dependencies {
    api("org.redisson:redisson-spring-boot-starter")

    testImplementation("com.redis.testcontainers:testcontainers-redis-junit:1.6.4")
//    testImplementation("org.testcontainers:junit-jupiter")
}