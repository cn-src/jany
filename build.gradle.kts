subprojects {
    apply(plugin = "maven-publish")
    group = "cn.javaer.jany"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
//        maven { url = uri("https://maven.aliyun.com/repository/public/") }
    }
    configure<PublishingExtension> {
        repositories {
            maven {
                val releasesRepoUrl = uri(System.getenv("publicReleasesRepoUrl"))
                val snapshotsRepoUrl = uri(System.getenv("publicSnapshotsRepoUrl"))
                val isSnapshot = (version as String).endsWith("SNAPSHOT")
                url = if (isSnapshot) snapshotsRepoUrl else releasesRepoUrl
                credentials {
                    username = System.getenv("publicRepoUsername")
                    password = System.getenv("publicRepoPassword")
                }
            }
        }
    }
}