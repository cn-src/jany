plugins {
    id("jany-library")
}
val archunitVersion: String by project

dependencies {
    api("com.tngtech.archunit:archunit-junit5:$archunitVersion")
}