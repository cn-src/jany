plugins {
    id("jany-library")
}

val hutoolVersion: String by project

dependencies {
    api("cn.hutool:hutool-core:$hutoolVersion")
    api(project(":jany-jackson"))

    optionalApi("org.skyscreamer:jsonassert")
    optionalApi("com.h2database:h2")
}