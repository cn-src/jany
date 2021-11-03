plugins {
    id("jany-library")
}
val p6spyVersion: String by project
dependencies {
    api("p6spy:p6spy:$p6spyVersion")
}