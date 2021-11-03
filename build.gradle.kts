subprojects {
    apply(plugin = "maven-publish")
    group = "cn.javaer.jany"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenLocal()
        maven { url = uri("https://maven.aliyun.com/repository/public/") }
        mavenCentral()
    }
    configure<PublishingExtension> {
        repositories {
            maven {
                val releasesRepoUrl = uri("$buildDir/repos/releases")
                val snapshotsRepoUrl = uri("$buildDir/repos/snapshots")
                val isSnapshot = (version as String).endsWith("SNAPSHOT")
                url = if (isSnapshot) snapshotsRepoUrl else releasesRepoUrl
            }
        }
    }
}