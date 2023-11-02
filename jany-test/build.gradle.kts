plugins {
    id("jany-library")
}

val hutoolVersion: String by project

dependencies {
    api("org.dromara.hutool:hutool-core:$hutoolVersion")
    api(project(":jany-jackson"))

    optionalApi("org.skyscreamer:jsonassert")
    optionalApi("com.h2database:h2")
}